Ext.define("AP.view.reservoirProperty.ReservoirPropertyInfoWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.reservoirPropertyInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'reservoirProperty_addwin_Id',
    closeAction: 'destroy',
    width: 330,
    shadow: 'sides',
    resizable: false,
    collapsible: true,
    maximizable: false,
    bodyStyle: 'padding:5px;background-color:#D9E5F3;',
    modal: true,
    border: false,
    initComponent: function () {
        var me = this;
        var ResNameInfoStore = Ext.create("AP.store.reservoirProperty.ResNameInfoStore");
        /**下拉机构数*/
        var xlrestree = Ext.create('AP.view.reservoirProperty.ResproTreeComboBox', {
            fieldLabel: cosog.string.resName,
            emptyText: cosog.string.checkRes,
            blankText: cosog.string.checkRes,
            id: 'yqcbh_Id1',
            anchor: '95%',
            callback: function (id, text) {
                var value_ = id
                var resName_ = text;
                Ext.getCmp("reservoirProperty_addwin_Id").down('form').getChildByElement("yqcbh_Id").setValue(id);
                // 检索动态列表头及内容信息
                Ext.Ajax.request({
                    method: 'POST',
                    params: {
                        resCode: value_
                    },
                    url: context + '/reservoirPropertyManagerController/judgeResExistOrNot',
                    success: function (response, opts) {
                        // 处理后json
                        var obj = Ext.decode(response.responseText);
                        var msg_ = obj.msg;
                        if (msg_ == "1") {
                            Ext.Msg.alert(cosog.string.ts, "sorry！<font color='red'>【" + cosog.string.resName + ":" + resName_ + "】</font>" + cosog.string.exist + "！");
                        }
                        // ==end
                    },
                    failure: function (response, opts) {
                        Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                    }
                });
            }
        });
        
        var ResTreeStore=Ext.create('Ext.data.TreeStore', {
            fields: ['resCode', 'text', 'leaf'],
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
        })
        
        var xlrestree=Ext.create('AP.view.well.TreePicker',{
        	id: 'yqcbh_Id1',
            anchor: '95%',
        	fieldLabel: cosog.string.resName,
        	emptyText: cosog.string.checkRes,
        	blankText: cosog.string.checkRes,
            displayField: 'text',
            autoScroll:true,
            forceSelection : true,// 只能选择下拉框里面的内容
            rootVisible: false,
            store:ResTreeStore,
            listeners: {
                select: function (picker,record,eOpts) {
                	var value_ = record.data.resCode
                    var resName_ = record.data.text;
                    Ext.getCmp("reservoirProperty_addwin_Id").down('form').getChildByElement("yqcbh_Id").setValue(record.data.resCode);
                    // 检索动态列表头及内容信息
                    Ext.Ajax.request({
                        method: 'POST',
                        params: {
                            resCode: value_
                        },
                        url: context + '/reservoirPropertyManagerController/judgeResExistOrNot',
                        success: function (response, opts) {
                            // 处理后json
                            var obj = Ext.decode(response.responseText);
                            var msg_ = obj.msg;
                            if (msg_ == "1") {
                                Ext.Msg.alert(cosog.string.ts, "sorry！<font color='red'>【" + cosog.string.resName + ":" + resName_ + "】</font>" + cosog.string.exist + "！");
                            }
                            // ==end
                        },
                        failure: function (response, opts) {
                            Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                        }
                    });
                }
            }
        });

        var postreservoirPropertyEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
                    xtype: "hidden",
                    fieldLabel: '序号',
                    id: 'jlbh_Id',
                    name: "reservoirProperty.jlbh"
       }, {
                    xtype: "hidden",
                    fieldLabel: '区块编号',
                    id: 'yqcbh_Id',
                    name: "reservoirProperty.yqcbh"
       }, xlrestree, {
                    id: 'yymd_Id',
                    fieldLabel: cosog.string.yymd,
                    value: '',
                    anchor: '95%',
                    name: "reservoirProperty.yymd"
       }, {
                    id: 'smd_Id',
                    anchor: '95%',
                    fieldLabel: cosog.string.ssmd,
                    value: '',
                    name: "reservoirProperty.smd"
       }, {
                    id: 'trqxdmd_Id',
                    fieldLabel: cosog.string.trqxdmd,
                    anchor: '95%',
                    value: '',
                    name: "reservoirProperty.trqxdmd"
       },
//							{
//								id : 'ysrjqyb_Id',
//								fieldLabel : cosog.string.ysrjqyb,
//								value : '',
//								anchor : '95%',
//								name : "reservoirProperty.ysrjqyb"
//							}, 
                {
                    id: 'bhyl_Id',
                    fieldLabel: cosog.string.bhyl,
                    value: '',
                    anchor: '95%',
                    name: "reservoirProperty.bhyl"
       },
//							{
//								id : 'dmtqyynd_Id',
//								fieldLabel : cosog.string.dmtqyynd,
//								value : '',
//								anchor : '95%',
//								name : "reservoirProperty.dmtqyynd"
//							}, 
                {
                    id: 'yqcyl_Id',
                    fieldLabel: cosog.string.yqcyl,
                    value: '',
                    anchor: '95%',
                    name: "reservoirProperty.yqcyl"
       }, {
                    id: 'yqczbsd_Id',
                    fieldLabel: cosog.string.yqczbsd,
                    value: '',
                    anchor: '95%',
                    name: "reservoirProperty.yqczbsd"
       }, {
                    id: 'yqczbwd_Id',
                    fieldLabel: cosog.string.yqczbwd,
                    value: '',
                    anchor: '95%',
                    name: "reservoirProperty.yqczbwd"
       }],
            buttons: [{
                id: 'addFormreservoirProperty_Id',
                xtype: 'button',
                text: cosog.string.save,
                iconCls: 'save',
                handler: SavereservoirPropertyDataInfoSubmitBtnForm
       }, {
                xtype: 'button',
                id: 'updateFormreservoirProperty_Id',
                text: cosog.string.update,
                iconCls: 'edit',
                hidden: true,
                handler: UpdatereservoirPropertyDataInfoSubmitBtnForm
       }, {
                text: cosog.string.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("reservoirProperty_addwin_Id")
                        .close();
                }
       }]
        });
        Ext.apply(me, {
            items: postreservoirPropertyEditForm
        });
        me.callParent(arguments);
    }
});