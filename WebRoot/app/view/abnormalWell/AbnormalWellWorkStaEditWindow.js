Ext.define('AP.view.abnormalWell.AbnormalWellWorkStaEditWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.abnormalWellWorkStaEditWindow',
    layout: 'fit',
    iframe: true,
    id: 'AbnormalWellInfo_addwin_Id',
    closeAction: 'destroy',
    width: 430,
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
        var gkjglyStore = Ext.create('Ext.data.Store', {
            fields: ['boxkey', 'boxval'],
            data : [
                {"boxkey":"0", "boxval":"软件计算"},
                {"boxkey":"1", "boxval":"人工干预"}
                //...
            ]
        });
        var gkjglyCombox= Ext.create('Ext.form.ComboBox', {
            fieldLabel: '工况结果来源',
            anchor: '95%',
            id: 'abnormalWellgkjgly_Id',
            store: gkjglyStore,
            queryMode: 'local',
            displayField: 'boxval',
            valueField: 'boxkey',
            listeners: {
                select: function () {
                    if(this.value=="0"){
                    	Ext.getCmp("delreWellInfoLabelClassBtn_Id").setDisabled(true)
                    };
                }
            }
        });
        
        var sxfwStore = Ext.create('Ext.data.Store', {
            fields: ['boxkey', 'boxval'],
            data : [
                {"boxkey":"0", "boxval":"针对单井"},
                {"boxkey":"1", "boxval":"针对单张功图"}
                //...
            ]
        });
        var sxfwCombox= Ext.create('Ext.form.ComboBox', {
            fieldLabel: '生效范围',
            anchor: '95%',
            id: 'abnormalWellsxfw_Id',
            store: sxfwStore,
            queryMode: 'local',
            displayField: 'boxval',
            valueField: 'boxkey'
        });
        
        var gklxStore = new Ext.data.JsonStore({
            fields: [{
                name: "boxkey",
                type: "string"
         }, {
                name: "boxval",
                type: "string"
         }],
            proxy: {
                url: context + '/abnormalWellManagerController/loadAbnormalGklx',
                type: "ajax",
                actionMethods:{
                	read:'POST'
                },
                reader:{
                	type:'json',
                	rootProperty:'list',
                	totalProperty:'totals'
                }
            },
            autoLoad: false,
            listeners: {
                beforeload: function (store, options) {
                	
                }
            }
        });
        var gklxCombox = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: cosog.string.gklx,
                anchor: '95%',
                id: 'abnormalWellgklx_Id1',
                hidden: false,
                //value:'1',
                store: gklxStore,
                queryMode: 'remote',
                emptyText: cosog.string.xzgk,
                blankText: cosog.string.xzgk,
                typeAhead: true,
                allowBlank: true,
                triggerAction: 'all',
                displayField: "boxval",
                valueField: "boxkey",
                listeners: {
                    select: function () {
                        Ext.getCmp("abnormalWellgklx_Id").setValue(this.value);
                    }
                }
            });
        
        var abnormalWellWindowEditFrom = Ext.create('Ext.form.Panel', {
        	baseCls: 'x-plain',
            defaultType: 'textfield',
            items: [{
                xtype: "hidden",
                id: 'abnormalWelljlbh_Id',
                anchor: '95%',
                name: "jlbh"
            }, {
                xtype: "hidden",
                id: 'abnormalWelljh_Id',
                anchor: '95%',
                name: "jbh"
            }, {
            	xtype: "hidden",
                id: 'abnormalWellgklx_Id',
                anchor: '95%',
                name: "gklx"
         },{
        	 fieldLabel: cosog.string.jh,
             id: 'abnormalWelljh_Id1',
             anchor: '95%',
             allowBlank: false,
             name: "jh"
         },{
             xtype      : 'fieldcontainer',
             fieldLabel : '生效范围',
             defaultType: 'radiofield',
             defaults: {
                 flex: 1
             },
             layout: 'hbox',
             items: [
                 {
                     boxLabel  : '实时功图(含最新功图)',
                     name      : 'sxfw',
                     inputValue: '0',
                     id        : 'abnormalWellsxfw1',
                     checked   : false
                 }, {
                     boxLabel  : '历史功图',
                     name      : 'sxfw',
                     inputValue: '1',
                     id        : 'abnormalWellsxfw2',
                     checked   : true
                 }
             ]
         },{
             xtype      : 'fieldcontainer',
             fieldLabel : '工况结果来源',
             defaultType: 'radiofield',
             defaults: {
                 flex: 1
             },
             layout: 'hbox',
             items: [
                 {
                     boxLabel  : '软件计算',
                     name      : 'gkjgly',
                     inputValue: '0',
                     id        : 'abnormalWellgkjgly1'
                     //checked   :true
                 }, {
                     boxLabel  : '人工干预',
                     name      : 'gkjgly',
                     inputValue: '1',
                     id        : 'abnormalWellgkjgly2'
                 }
             ]
         },
         gklxCombox,
         {
             xtype: 'datefield',
             id: 'abnormalWellkssj_Id',
             fieldLabel: '起始时间',
             format: 'Y-m-d',
             anchor: '95%',
             altFormats: 'm,d,Y|m.d.Y',
             value: new Date(),
             name: "kssj"
         },{
             xtype: 'datefield',
             id: 'abnormalWelljssj_Id',
             fieldLabel: '结束时间',
             format: 'Y-m-d',
             anchor: '95%',
             altFormats: 'm,d,Y|m.d.Y',
             value: new Date(),
             name: "jssj"
         }],
            buttons: [{
                xtype: 'button',
                id: 'updateFromAbnormalWellBtn_Id',
                text: cosog.string.update,
                hidden: false,
                iconCls: 'edit',
                handler: UpdateAbnormalWellSubmitBtnForm
         }, {
                text: cosog.string.cancel,
                iconCls: 'cancel',
                handler: function () {
                    Ext.getCmp("AbnormalWellInfo_addwin_Id").close();
                }
         }]
        });
        Ext.apply(me, {
        	title: '工况人工干预',
            items: abnormalWellWindowEditFrom
        })
        me.callParent(arguments);
    }
})


