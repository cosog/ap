var res_Level;
Ext.define("AP.view.res.ResInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.resInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'res_addwin_Id',
    closeAction: 'destroy',
    width: 320,
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
        //var ResParentInfoStore = Ext.create("AP.store.res.ResParentInfoStore");
        /**下拉机构数*/
        var ResTreeStore=Ext.create('Ext.data.TreeStore', {
            fields: ['resId', 'text', 'leaf'],
            autoLoad: false,
            proxy: {
                type: 'ajax',
                url: context + '/resManagerController/constructResTreeGridTree',
                reader: 'json'
            },
            root: {
                expanded: true,
                text: 'orgName'
            }
        });
        var xltree=Ext.create('AP.view.well.TreePicker',{
        	id: 'resName_Parent_Id1',
            anchor: '95%',
            value: '',
            fieldLabel: cosog.string.superRes,
            emptyText: cosog.string.checkRes,
            blankText: cosog.string.checkRes,
            displayField: 'text',
            autoScroll:true,
            forceSelection : true,// 只能选择下拉框里面的内容
            rootVisible: false,
            store:ResTreeStore,
            listeners: {
                select: function (picker,record,eOpts) {
                	Ext.getCmp("res_addwin_Id").down('form').getChildByElement("resName_Parent_Id").setValue(record.data.resId);
                    var record_ = record.data.resId;
                    // 动态返回当前区块级别下的的最大maxId值
                    Ext.Ajax.request({
                        method: 'POST',
                        url: context + '/resManagerController/findResChildrenByparentId',
                        // 提交参数
                        params: {
                            parentId: record_
                        },
                        success: function (response, opts) {
                            Ext.getCmp("resCode_hidden_Id").setValue(response.responseText);
                        },
                        failure: function (response, opts) {
                            Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                        }
                    });
                }
            }
        });
        var postresEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            id: 'addresForm_Id',
            defaultType: 'textfield',
            items: [{
                xtype: "hidden",
                fieldLabel: '区块序号',
                id: 'resres_Id',
                anchor: '95%',
                name: "res.resId"
     }, {
                xtype: "hidden",
                value: "0",
                fieldLabel: '区块父ID',
                anchor: '95%',
                name: 'res.resParent',
                id: 'resName_Parent_Id'

     }, {
                xtype: "hidden",
                fieldLabel: '区块Level',
                anchor: '95%',
                name: 'res.resLevel',
                id: 'resLevel_Id'

     }, {
                xtype: "hidden",
                fieldLabel: 'rescode',
                id: 'resCode_hidden_Id'

     }, xltree, {
                xtype: "combobox",
                anchor: '95%',
                fieldLabel: cosog.string.resLevel,
                id: 'resLevel_Id1',
                triggerAction: 'all',
                selectOnFocus: true,
                forceSelection: true,
                allowBlank: false,
                store: new Ext.data.SimpleStore({
                    fields: ['value', 'text'],
                    data: [['1', cosog.string.resOne], ['2', cosog.string.resTwo],
           ['3', cosog.string.resThree], ['4', cosog.string.resFour],
           ['5', cosog.string.resFive]]
                }),
                displayField: 'text',
                valueField: 'value',
                mode: 'local',
                emptyText: cosog.string.checkLevel,
                blankText: cosog.string.checkLevel,
                listeners: {
                    select: function (combo, record, index) {
                        try {
                            Ext.getCmp("resLevel_Id").setValue(this.value);
                            var myres_store = Ext.getCmp("ResHeadInfoGridPanel_Id").getStore();
                            var resName_Parent_Id = Ext.getCmp('resName_Parent_Id').getValue();
                            var nodes_num = 0;
                            var resCode_;
                            var childs_ = myres_store.root.childNodes.length;
                            var levelValue = this.value;
                            if (resName_Parent_Id == '0' || resName_Parent_Id == "") {
                                resCode_ = "0" + (childs_ + 1) + levelValue + "00";
                            } else {
                                var obj_text = Ext.getCmp("resCode_hidden_Id").getValue();
                                if (obj_text != "") {
                                    var obj = Ext.decode(obj_text);
                                    nodes_num = obj.childNodes;
                                    var t_resCode_ = obj.resCode;
                                    if (nodes_num == 0) {
                                        t_resCode_ = t_resCode_ + '00';
                                    } else {
                                    	if(nodes_num>=10){
                                    		t_resCode_ = t_resCode_ + "" + (nodes_num);
                                    	}else{
                                    		t_resCode_ = t_resCode_ + "0" + (nodes_num);
                                    	}
                                    }
                                    resCode_ = t_resCode_;
                                }
                            }
                            if (isNotVal(resCode_)) {
                                Ext.getCmp("resCode_Id").setValue(resCode_);
                            }
                        } catch (ex) {
                            Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                        }
                    }
                }
     }, {
                fieldLabel: cosog.string.resName,
                id: 'resName_Id',
                anchor: '95%',
                name: "res.resName"
     }, {
                id: 'resCode_Id',
                fieldLabel: cosog.string.rescode,
                anchor: '95%',
                value: '',
                name: "res.resCode"
     }, {
                xtype: "textfield",
                fieldLabel: cosog.string.resOrder,
                anchor: '95%',
                id: 'resSeq_Id',
                name: "res.resSeq",
                value: 1

     }, {
                fieldLabel: cosog.string.resMemo,
                id: 'resMemo_Id',
                xtype: 'textareafield',
                anchor: '95%',
                value: '',
                name: "res.resMemo"
     }],
            buttons: [{
                id: 'addFormres_Id',
                xtype: 'button',
                text: cosog.string.save,
                iconCls: 'save',
                handler: SaveresDataInfoSubmitBtnForm
     }, {
                xtype: 'button',
                id: 'updateFormres_Id',
                text: cosog.string.update,
                iconCls: 'edit',
                hidden: true,
                handler: UpdateresDataInfoSubmitBtnForm
     }, {
                text: cosog.string.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("res_addwin_Id").close();
                }
     }]
        });
        Ext.apply(me, {
            items: postresEditForm
        });
        me.callParent(arguments);
    }
});