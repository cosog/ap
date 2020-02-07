var org_Level;
Ext.define("AP.view.org.OrgInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.orgInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'org_addwin_Id',
    closeAction: 'destroy',
    width: 330,
    //height : 300,
    constrain: true,
    shadow: 'sides',
    resizable: false,
    collapsible: true,
    maximizable: false,
    layout: 'fit',
    plain: true,
    bodyStyle: 'padding:5px;background-color:#D9E5F3;',
    modal: true,
    border: false,
    initComponent: function () {
        var me = this;
        /**下拉机构数*/
        var OrgTreeStore=Ext.create('Ext.data.TreeStore', {
            fields: ['orgId', 'text', 'leaf'],
            autoLoad: false,
            proxy: {
                type: 'ajax',
                url: context + '/orgManagerController/constructOrgRightTree',
                reader: 'json'
            },
            root: {
                expanded: true,
                text: 'orgName'
            },
            listeners: {
            	beforeload: function (store, options) {
                	var orgId = Ext.getCmp('leftOrg_Id').getValue();
                    var org_name_Id = Ext.getCmp('org_name_Id');
                    if (!Ext.isEmpty(org_name_Id)) {
                        org_name_Id = org_name_Id.getValue();
                    }
                    var new_params = {
                    	orgId:orgId,
                        orgName: org_name_Id
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var xltree=Ext.create('AP.view.well.TreePicker',{
        	id:'orgName_Parent_Id1',
        	anchor: '95%',
        	fieldLabel: cosog.string.superOrg,
            emptyText: cosog.string.chooseOrg,
            blankText: cosog.string.chooseOrg,
            displayField: 'text',
            autoScroll:true,
            forceSelection : true,// 只能选择下拉框里面的内容
            rootVisible: false,
            store:OrgTreeStore,
            listeners: {
                select: function (picker,record,eOpts) {
                	Ext.getCmp("org_addwin_Id").down('form').getChildByElement("orgName_Parent_Id").setValue(record.data.orgId);
                	var record_=record.data.orgId;
    		    	// 动态返回当前单位级别下的的最大maxId值
    				Ext.Ajax.request({
    					method : 'POST',
    					url : context+ '/orgManagerController/findOrgChildrenByparentId',
    					// 提交参数
    					params : {
    						parentId : record_
    					},
    					success : function(response, opts) {
    						Ext.getCmp("orgCode_hidden_Id").setValue(response.responseText);
    					},
    					failure : function(response, opts) {
    						Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
    					}
    				});
                }
            }
        });
        var orgTypeStore = new Ext.data.SimpleStore({
            fields: [{
                name: "boxkey",
                type: "string"
         }, {
                name: "boxval",
                type: "string"
         }],
            proxy: {
                url: context + '/orgManagerController/loadOrgType',
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
                        type: 'yhlx'
                    };
                    Ext.apply(store.proxy.extraParams,
                        new_params);
                }
            }
        });

        // Simple ComboBox using the data store
        var OrgTypeCombox = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: cosog.string.orgType,
                name: 'org.orgType',
                id: 'org_Type_Id',
                anchor: '95%',
                store: orgTypeStore,
                queryMode: 'local',
                emptyText: cosog.string.all,
                blankText: cosog.string.all,
                typeAhead: true,
                allowBlank: true,
                triggerAction: 'all',
                displayField: "boxval",
                valueField: "boxkey",
                listeners: {
                    select: function () {
                        org_Level = this.value;
                        Ext.getCmp("orgLevel_Id").setValue(org_Level);
                        try {
                            var myOrg_store = Ext.getCmp("OrgInfoTreeGridView_Id").getStore();
                            var orgName_Parent_Id = Ext.getCmp('orgName_Parent_Id').getValue();
                            var nodes_num = 0;
                            var orgCode_;
                            //var childs_ = myOrg_store.tree.root.childNodes.length;
                            var childs_ = myOrg_store.root.childNodes.length;
                            if (orgName_Parent_Id == '0' || orgName_Parent_Id == "") {
                                orgCode_ = "0" + (childs_ + 1) + org_Level + "00";
                            } else {
                                var obj_text = Ext.getCmp("orgCode_hidden_Id").getValue();
                                if (obj_text != "") {
                                    var obj = Ext.decode(obj_text);
                                    // alert(t_orgCode_);
                                    nodes_num = obj.childNodes;
                                    // alert("nodes_num==" + nodes_num);
                                    var t_orgCode_ = obj.orgCode;
                                    // var nodes_ = '';
                                    if (nodes_num == 0) {
                                        t_orgCode_ = t_orgCode_ + '00';
                                    } else {
                                    	if(nodes_num>=10){
                                    		t_orgCode_ = t_orgCode_ + "" + (nodes_num);
                                    	}else{
                                    		t_orgCode_ = t_orgCode_ + "0" + (nodes_num);
                                    	}
                                    }
                                    orgCode_ = t_orgCode_;

                                }
                            }
                            // 动态返回当前单位级别下的的最大maxId值
                            Ext.Ajax.request({
                                method: 'POST',
                                url: context + '/orgManagerController/findCurrentOrgCodeIsNotExist',
                                // 提交参数
                                params: {
                                    orgCode: orgCode_
                                },
                                success: function (response, opts) {
                                    var obj = Ext.decode(response.responseText);
                                    var newcode_ = obj.orgCode;
                                    if (newcode_ != '0000') {
                                        //Ext.Msg.alert("信息提示",'抱歉该组织编码已经存在!');
                                        Ext.getCmp("orgCode_Id").setValue(newcode_);
                                    } else {
                                        Ext.getCmp("orgCode_Id").setValue(orgCode_);
                                    }
                                },
                                failure: function (response, opts) {
                                    Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                                }
                            });
                        } catch (ex) {
                            Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                        }
                    }
                }
            });

        var postOrgEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            id: 'addOrgForm_Id',
            defaultType: 'textfield',
            items: [{
                xtype: "hidden",
                fieldLabel: '单位序号',
                id: 'orgOrg_Id',
                value: '1',
                name: "org.orgId"
     }, {
                xtype: "hidden",
                fieldLabel: '单位父ID',
                value: '0',
                name: 'org.orgParent',
                id: 'orgName_Parent_Id'

     }, {
                xtype: "hidden",
                fieldLabel: 'orgcode',
                id: 'orgCode_hidden_Id'

     }, xltree, OrgTypeCombox, {
                fieldLabel: cosog.string.orgName,
                id: 'orgName_Id',
                anchor: '95%',
                name: "org.orgName"
     }, {
                id: 'orgCode_Id',
                fieldLabel: cosog.string.orgCode,
                value: '',
                anchor: '95%',
                name: "org.orgCode"
     }, {
                xtype: "textfield",
                fieldLabel: cosog.string.orgLevel,
                id: 'orgLevel_Id',
                anchor: '95%',
                name: "org.orgLevel",
                value: 1

     }, {
         		xtype: "textfield",
         		fieldLabel: cosog.string.longitude,
         		hidden:true,
         		id: 'orgCoordX_Id',
         		anchor: '95%',
         		name: "org.orgCoordX",
         		value:'0'

     }, {
   	  			 fieldLabel:cosog.string.latitude,
   	  			 hidden:true,
   	  			 id: 'orgCoordY_Id',
   	  			 anchor: '95%',
   	  			 name: "org.orgCoordY",
   	  			 value: '0'
     },{
         fieldLabel: cosog.string.showLevel,
         hidden:true,
         id: 'orgShowLevel_Id',
         value: '1',
         anchor: '95%',
         xtype: 'numberfield',
         maxValue: 19,
         minValue: 1,
         name: "org.showlevel"
     }, {
                fieldLabel: cosog.string.orgMemo,
                id: 'orgMemo_Id',
                anchor: '95%',
                xtype: 'textareafield',
                value: '',
                name: "org.orgMemo"
     }],
            buttons: [{
                id: 'addFormOrg_Id',
                xtype: 'button',
                text: cosog.string.save,
                iconCls: 'save',
                handler: SaveOrgDataInfoSubmitBtnForm
     }, {
                xtype: 'button',
                id: 'updateFormOrg_Id',
                text: cosog.string.update,
                hidden: true,
                iconCls: 'edit',
                handler: UpdateOrgDataInfoSubmitBtnForm
     }, {
                text: cosog.string.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("org_addwin_Id").close();
                }
     }]
        });
        Ext.apply(me, {
            items: postOrgEditForm
        });
        me.callParent(arguments);
    }
});