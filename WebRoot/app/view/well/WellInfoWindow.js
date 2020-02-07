Ext.define('AP.view.well.WellInfoWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.wellInfoWindow',
    layout: 'fit',
    iframe: true,
    id: 'wellInfo_addwin_Id',
    closeAction: 'destroy',
    width: 330,
    shadow: 'sides',
    resizable: false,
    collapsible: true,
    maximizable: false,
    constrain: true,
    plain: true,
    bodyStyle: 'padding:5px;background-color:#D9E5F3;',
    modal: true,
    border: false,
    initComponent: function () {
        var me = this;
        /**下拉机构数*/
//        var xlorgtree = Ext.create('AP.view.well.WellTreeComboBox', {
//            fieldLabel: cosog.string.orgName,
//            emptyText: cosog.string.chooseOrg,
//            blankText: cosog.string.chooseOrg,
//            id: 'dwbh_Id1',
//            anchor: '95%',
//            callback: function (id, text) {
//                Ext.getCmp("wellInfo_addwin_Id").down('form').getChildByElement("dwbh_Id").setValue(id);
//            }
//        });
        var OrgTreeStore=Ext.create('Ext.data.TreeStore', {
        	autoLoad:false,
        	proxy : {
				type : 'ajax',
				actionMethods : 'post',
				url : context+ '/orgManagerController/constructOrgTree2',
				reader : {
					type : 'json',
					rootProperty : 'list'
				},
				extraParams : {  
                    tid : '0'  
                } 
			},
			root:{
				text:'组织导航',
				expanded:true,
				orgId:'0'
			},
            listeners: {
            	nodebeforeexpand : function(node,eOpts) {
					this.proxy.extraParams.tid = node.data.orgId;
				},
				load: function (store, records, successful, operation, node, eOpts) {
				}
            }
        });
        var xlorgtree=Ext.create('AP.view.well.TreePicker',{
        	id:'dwbh_Id1',
        	fieldLabel: cosog.string.orgName,
        	anchor: '95%',
            emptyText: cosog.string.chooseOrg,
            blankText: cosog.string.chooseOrg,
            displayField: 'text',
            autoScroll:true,
            forceSelection : true,// 只能选择下拉框里面的内容
            rootVisible: false,
            store:OrgTreeStore,
            listeners: {
                expand: function (sm, selections) {
                },
                specialkey: function (field, e) {
                },
                select: function (picker,record,eOpts) {
                	Ext.getCmp("wellInfo_addwin_Id").down('form').getChildByElement("dwbh_Id").setValue(record.data.orgCode);
                }
            }
        });
        /**下拉机构数*/
        var ResTreeStore=Ext.create('Ext.data.TreeStore', {
        	autoLoad:false,
        	proxy : {
				type : 'ajax',
				actionMethods : 'post',
				url: context + '/resManagerController/constructResTreeGridTree',
				reader : {
					type : 'json',
					rootProperty : 'children'
				},
				extraParams : {  
                    tid : '0'  
                } 
			},
			root:{
				text:'区块',
				expanded:true,
				orgId:'0'
			},
            listeners: {
            	nodebeforeexpand : function(node,eOpts) {
					this.proxy.extraParams.tid = node.data.resId;
				}
            }
        });
        
        var xlrestree=Ext.create('AP.view.well.TreePicker',{
        	id:'yqcbh_Id1',
        	fieldLabel: cosog.string.resName,
        	emptyText: cosog.string.checkRes,
        	blankText: cosog.string.checkRes,
            anchor: '95%',
            displayField: 'text',
            hidden:true,
            autoScroll:true,
            forceSelection : true,// 只能选择下拉框里面的内容
            rootVisible: false,
            store:ResTreeStore,
            listeners: {
                select: function (picker,record,eOpts) {
                	Ext.getCmp("wellInfo_addwin_Id").down('form').getChildByElement("yqcbh_Id").setValue(record.data.resCode);
                }
            }
        });

        /**下拉机构数*/
        
        var JlxTreeStore=Ext.create('Ext.data.TreeStore', {
        	autoLoad:false,
        	proxy : {
				type : 'ajax',
				actionMethods : 'post',
				url: context + '/wellInformationManagerController/showWellTypeTree',
				reader : {
					type : 'json',
					rootProperty : 'children'
				},
				extraParams : {  
                    tid : '0'  
                } 
			},
			root:{
				text:'井类型',
				expanded:true,
				id:'0'
			},
            listeners: {
            	nodebeforeexpand : function(node,eOpts) {
					this.proxy.extraParams.tid = node.data.id;
				}
            }
        });
        var xlwelltypetree=Ext.create('AP.view.well.TreePicker',{
        	id:'jlx_Id1',
        	fieldLabel: cosog.string.jlx,
        	emptyText: cosog.string.checkJlx,
        	blankText: cosog.string.checkJlx,
            anchor: '95%',
            displayField: 'text',
            autoScroll:true,
            forceSelection : true,// 只能选择下拉框里面的内容
            rootVisible: false,
            store:JlxTreeStore,
            listeners: {
                select: function (picker,record,eOpts) {
                	Ext.getCmp("wellInfo_addwin_Id").down('form').getChildByElement("jlx_Id").setValue(record.data.id.substring(0, 3));
                }
            }
        });
        
        /**下拉举升类型树*/
        var JslxTreeStore=Ext.create('Ext.data.TreeStore', {
            fields: ['id', 'text', 'leaf'],
            autoLoad: false,
            proxy: {
                type: 'ajax',
                //url : context+ '/app/data/lift.json',
                url: context + '/productionDataController/showLiftTypeTree',
                reader: 'json'
            },
            root: {
                expanded: true,
                text: 'root'
            }
        });
        var xljslxtree=Ext.create('AP.view.well.TreePicker',{
        	fieldLabel: cosog.string.jslx,
            emptyText: cosog.string.chooseJslx,
            blankText: cosog.string.chooseJslx,
            id: 'jslx_Id1',
            anchor: '95%',
            displayField: 'text',
            autoScroll:true,
            forceSelection : true,// 只能选择下拉框里面的内容
            rootVisible: false,
            store:JslxTreeStore,
            listeners: {
                select: function (picker,record,eOpts) {
                	Ext.getCmp("jslx_Id").setValue(record.data.id);
                }
            }
        });

        var SsjwTypeStore = new Ext.data.SimpleStore({
            fields: [{
                name: "boxkey",
                type: "string"
         }, {
                name: "boxval",
                type: "string"
         }],
            proxy: {
                url: context + '/wellInformationManagerController/loadSsjwType',
                type: "ajax"
            },
            autoLoad: true,
            listeners: {
                beforeload: function (store, options) {
                    var new_params = {
                        type: 'ssjw'
                    };
                    Ext.apply(store.proxy.extraParams,
                        new_params);
                }
            }
        });

        // Simple ComboBox using the data store
        var SsjwTypeCombox = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: cosog.string.ssjw,
                anchor: '95%',
                id: 'ssjw_Id1',
                hidden: true,
                //value:'1',
                store: SsjwTypeStore,
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
                        Ext.getCmp("ssjw_Id").setValue(
                            //this.value
                            1);
                    }
                }
            });
        var SszcdyTypeStore = new Ext.data.SimpleStore({
            fields: [{
                name: "boxkey",
                type: "string"
         }, {
                name: "boxval",
                type: "string"
         }],
            proxy: {
                url: context + '/wellInformationManagerController/loadSszcdyType',
                type: "ajax"
            },
            autoLoad: true,
            listeners: {
                beforeload: function (store, options) {
                    var new_params = {
                        type: 'sszcdy'
                    };
                    Ext.apply(store.proxy.extraParams,
                        new_params);
                }
            }
        });

        // Simple ComboBox using the data store
        var SszcdyTypeCombox = Ext.create(
            'Ext.form.field.ComboBox', {
                anchor: '95%',
                fieldLabel: cosog.string.sszcdy,
                id: 'sszcdy_Id1',
                hidden: true,
                //value:'1',
                store: SszcdyTypeStore,
                emptyText: cosog.string.all,
                blankText: cosog.string.all,
                typeAhead: true,
                allowBlank: true,
                triggerAction: 'all',
                displayField: "boxval",
                valueField: "boxkey",
                listeners: {
                    select: function () {
                        Ext.getCmp("sszcdy_Id").setValue(
                            //this.value
                            1);
                    }
                }
            });
        var JcStore = new Ext.data.SimpleStore({
            fields: [{
                name: "boxkey",
                type: "string"
         }, {
                name: "boxval",
                type: "string"
         }],
            proxy: {
            	url: context + '/wellInformationManagerController/showWellsiteList',
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
                	//
                }
            }
        });
        var jcCombox = Ext.create(
                'Ext.form.field.ComboBox', {
                    fieldLabel: cosog.string.jz,
                    id: 'jc_Id',
                    name: "wellInformation.jc",
                    anchor: '95%',
                    store: JcStore,
                    emptyText: cosog.string.checkJzlx,
                    blankText: cosog.string.checkJzlx,
                    typeAhead: true,
                    allowBlank: true,
                    editable:false,
                    triggerAction: 'all',
                    displayField: "boxval",
                    valueField: "boxkey",
                    listeners: {
                        select: function (combo, record, index) {
                        	Ext.getCmp("jclx_Id").setValue(record.data.boxkey);
                        }
                    }
                });
        var wellWindowEditFrom = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
                xtype: 'hidden',
                fieldLabel: '序号',
                id: 'jlbh_Id',
                anchor: '95%',
                name: "wellInformation.jlbh"
         }, {
                xtype: 'hidden',
                fieldLabel: '',
                name: "wellInformation.dwbh",
                id: 'dwbh_Id'
         }, {
                xtype: 'hidden',
                fieldLabel: '',
                name: "wellInformation.yqcbh",
                id: 'yqcbh_Id'
         }, {
                xtype: 'hidden',
                fieldLabel: '',
                name: "wellInformation.jlx",
                id: 'jlx_Id'

         },{
             xtype: 'hidden',
             fieldLabel: '',
             name: "wellInformation.jslx",
             id: 'jslx_Id'

         }, {
                xtype: 'hidden',
                fieldLabel: '',
                value: '1',
                name: "wellInformation.ssjw",
                id: 'ssjw_Id'

         }, {
                xtype: 'hidden',
                fieldLabel: '',
                value: '1',
                name: "wellInformation.sszcdy",
                id: 'sszcdy_Id'

         }, xlorgtree, xlrestree,jcCombox,
//         {
//                fieldLabel: cosog.string.jc,
//                anchor: '95%',
//                id: 'jc_Id',
//                name: "wellInformation.jc"
//         }, 
         {
                fieldLabel: cosog.string.jhh,
                id: 'jhh_Id',
                hidden: sfycjhh,
                anchor: '95%',
                name: "wellInformation.jhh"
         }, {
                fieldLabel: cosog.string.jh,
                id: 'jh_Id',
                anchor: '95%',
                allowBlank: false,
                name: "wellInformation.jh",
                listeners: {
                    blur: function (t, e) {
                        var value_ = t.getValue();
                        // 检索动态列表头及内容信息
                        Ext.Ajax.request({
                            method: 'POST',
                            params: {
                                jh: t.value
                            },
                            url: context + '/wellInformationManagerController/judgeWellExistOrNot',
                            success: function (response, opts) {
                                // 处理后json
                                var obj = Ext.decode(response.responseText);
                                var msg_ = obj.msg;
                                if (msg_ == "1") {
                                    Ext.Msg.alert(cosog.string.ts, "sorry！<font color='red'>【" + cosog.string.jh + ":" + value_ + "】</font>" + cosog.string.exist + "！");
                                    t.setValue("");
                                }

                                // ==end
                            },
                            failure: function (response, opts) {
                                Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                            }
                        });
                    }
                }
         }, xlwelltypetree,xljslxtree, SsjwTypeCombox, SszcdyTypeCombox, {
                fieldLabel: cosog.string.rgzsjd,
                id: 'rgzsjd_Id',
                anchor: '95%',
                hidden: false,
                value: dateFormatNotDa(new Date()) + ';3;00:00-24:00',
                name: "wellInformation.rgzsjd",
                listeners: {
                    blur: function () {
                        var vary_ = this.value.split(";");
                        var hour_ = countHour(vary_[2]);
                        Ext.getCmp("rgzsj_Id").setValue(hour_);
                    }
                }
         }, {
                fieldLabel: cosog.string.rgzsj,
                id: 'rgzsj_Id',
                value: '24',
                anchor: '95%',
                xtype: 'numberfield',
                maxValue: 24.00,
                minValue: 0.00,
                name: "wellInformation.rgzsj"
         },{
             fieldLabel:cosog.string.longitude,
             id: 'coordX_Id',
             anchor: '95%',
             hidden: true,
             name: "wellInformation.dmx",
             listeners: {
                 //
             }
      },{
    	 
            	 fieldLabel:cosog.string.latitude,
                 id: 'coordY_Id',
                 anchor: '95%',
                 hidden: true,  
                 name: "wellInformation.dmy"
           
      },{
          fieldLabel: cosog.string.showLevel,
          hidden:true,
          id: 'wellShowLevel_Id',
          value: '1',
          anchor: '95%',
          xtype: 'numberfield',
          maxValue: 19,
          minValue: 1,
          name: "wellInformation.showlevel"
      }],
            buttons: [{
                xtype: 'button',
                id: 'addFromwellBtn_Id',
                text: cosog.string.save,
                iconCls: 'save',
                handler: SavewellInfoSubmitBtnForm
         }, {
                xtype: 'button',
                id: 'updateFromwellBtn_Id',
                text: cosog.string.update,
                hidden: true,
                iconCls: 'edit',
                handler: UpdatewellInfoSubmitBtnForm
         }, {
                text: cosog.string.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("wellInfo_addwin_Id").close();
                }
         }]
        });
        Ext.apply(me, {
            items: wellWindowEditFrom
        })
        me.callParent(arguments);
    }
})

function computeHour(val) {
    var times_ = val.split("-");
    var time1 = times_[0].split(":");
    var time2 = times_[1].split(":");
    var date2 = parseInt(time2[0]) * 60 + parseInt(time2[1]);
    var date1 = parseInt(time1[0]) * 60 + parseInt(time1[1]);
    var nHour;
    nHour = parseFloat((date2 - date1) / 60.0);
    // nHour=nHour.toFixed(2)
    //alert(nHour);
    return nHour;
}

function countHour(val) {
    var vary_ = val.split(",");
    var i = 0;
    var nHour1 = 0;
    for (i = 0; i < vary_.length; i++) {
        var times_ = vary_[i].split("-");
        var time1 = times_[0].split(":");
        var time2 = times_[1].split(":");
        var date2 = parseInt(time2[0]) * 60 + parseInt(time2[1]);
        var date1 = parseInt(time1[0]) * 60 + parseInt(time1[1]);
        var nHour = parseFloat((date2 - date1) / 60.0);
        nHour1 = nHour1 + nHour;
        nHour1 = Number(nHour1.toFixed(2));
    }

    return nHour1;
}