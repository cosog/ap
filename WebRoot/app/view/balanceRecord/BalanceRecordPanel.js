/*******************************************************************************
 * 调平衡记录查询视图
 *
 * @author zhao
 *
 */

Ext.define("AP.view.balanceRecord.BalanceRecordPanel", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.balanceRecordPanel', // 定义别名
    id:'BalanceRecordPanel_Id',
    layout: 'border',
    border: false,
    initComponent: function () {
        var me = this;
        var BalanceRecordStore=Ext.create("AP.store.balanceRecord.BalanceRecordStore");
        var jhStore_A = new Ext.data.JsonStore({
        	pageSize:defaultJhhComboxSize,
            fields: [{
                name: "boxkey",
                type: "string"
            }, {
                name: "boxval",
                type: "string"
            }],
            proxy: {
                url: context + '/monitorPumpingUnitParamsManagerController/queryMonitorPUJhh',
                type: "ajax",
                actionMethods: {
                    read: 'POST'
                },
                reader: {
                    type: 'json',
                    rootProperty: 'list',
                    totalProperty: 'totals'
                }
            },
            autoLoad: false,
            listeners: {
                beforeload: function (store, options) {
                    var org_Id = Ext.getCmp('leftOrg_Id').getValue();
                    var jh_tobj = Ext.getCmp('BalanceRecordjh_Id').getValue();
                    var new_params = {
                    	jh:jh_tobj,
                        type: 'jh',
                        orgId: org_Id
                    };
                    Ext.apply(store.proxy.extraParams, new_params);
                }
            }
        });
        var jhComboBox = Ext.create('Ext.form.field.ComboBox', {
            fieldLabel: cosog.string.jh,
            id: "BalanceRecordjh_Id",
            store:jhStore_A,
            labelWidth: 35,
            width:145,
            queryMode: 'remote',
            typeAhead: true,
            autoSelect: false,
            editable: true,
            triggerAction: 'all',
            displayField: "boxval",
            emptyText: cosog.string.all,
            blankText: cosog.string.all,
            valueField: "boxkey",
            pageSize:comboxPagingStatus,
            minChars:0,
            multiSelect:false, 
            listeners: {
                expand: function (sm, selections) {
//                	jhComboBox.clearValue();
                	jhComboBox.getStore().load();
                },
                select: function (combo, record, index) {
                	Ext.getCmp("BalanceRecordGridPanel_Id").getStore().load();
                }
            }
        });
        Ext.apply(me, {
        	tbar:[jhComboBox,{
                xtype: 'datefield',
                anchor: '100%',
                fieldLabel: cosog.string.startDate,
                labelWidth: 58,
                width: 178,
                format: 'Y-m-d ',
                id: 'BalanceRecordStartDate_Id',
                value: 'new',
                listeners: {
                	select: function (combo, record, index) {
                		Ext.getCmp("BalanceRecordGridPanel_Id").getStore().load();
                    }
                }
            }, {
                xtype: 'datefield',
                anchor: '100%',
                fieldLabel: cosog.string.endDate,
                labelWidth: 58,
                width: 178,
                format: 'Y-m-d',
                id: 'BalanceRecordEndDate_Id',
                value: 'new',
                listeners: {
                	select: function (combo, record, index) {
                		Ext.getCmp("BalanceRecordGridPanel_Id").getStore().load();
                    }
                }
            },{
                xtype: 'button',
                text: cosog.string.exportExcel,
                pressed: true,
                hidden:true,
                handler: function (v, o) {
                	
                }
            },{
                xtype: 'button',
                text: cosog.string.exportExcel,
                pressed: true,
                handler: function (v, o) {
                	exportBalanceRecorsExcelData();
                	var gridId="BalanceRecordGridPanel_Id";
//                	var url=context + '/balanceRecordController/exportExcelBalanceRecordData';
//                	var fileName=cosog.string.bananceRecordExcel;
//                	var title=cosog.string.bananceRecordExcel;
//                	exportGridExcelData(gridId,url,fileName,title);
                }
            }, '->', {
                id: 'BalanceRecordTotalCount_Id',
                xtype: 'component',
                tpl: cosog.string.totalCount + ': {count}',
                style: 'margin-right:15px'
            }],
            items: [{
            	region: 'center',
            	id:'BalanceRecordTablePanel_Id',
            	layout: 'fit',
                border: false
            }]
        });
        me.callParent(arguments);
    }

});

function exportBalanceRecorsExcelData(){
    var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
    var jh = Ext.getCmp('BalanceRecordjh_Id').getValue();
    var startDate = Ext.getCmp('BalanceRecordStartDate_Id').rawValue;
    var endDate = Ext.getCmp('BalanceRecordEndDate_Id').rawValue;
    var fields = "";
    var heads = "";
    var gridPanel = Ext.getCmp("BalanceRecordGridPanel_Id");
    var items_ = gridPanel.items.items;
    if(items_.length==1){//无锁定列时
    	var columns_ = gridPanel.columns;
    	Ext.Array.each(columns_, function (name,index, countriesItSelf) {
                var locks = columns_[index];
                if (index > 0 && locks.hidden == false) {
                    fields += locks.dataIndex + ",";
                    heads += locks.text + ",";
                }
            });
    }else{
    	Ext.Array.each(items_, function (name, index,countriesItSelf) {
                var datas = items_[index];
                var columns_ = datas.columns;
                if (index == 0) {
                    Ext.Array.each(columns_, function (name,
                        index, countriesItSelf) {
                        var locks = columns_[index];
                        if (index > 0 && locks.hidden == false) {
                            fields += locks.dataIndex + ",";
                            heads += locks.text + ",";
                        }
                    });
                } else {
                    Ext.Array.each(columns_, function (name,
                        index, countriesItSelf) {
                        var headers_ = columns_[index];
                        if (headers_.hidden == false) {
                            fields += headers_.dataIndex + ",";
                            heads += headers_.text + ",";
                        }
                    });
                }
            });
    }
    if (isNotVal(fields)) {
        fields = fields.substring(0, fields.length - 1);
        heads = heads.substring(0, heads.length - 1);
    }
    fields = "id," + fields;
    var param = "&fields=" + fields + "&orgId=" + leftOrg_Id + "&jh=" + URLencode(URLencode(jh))  + "&startDate=" + startDate + "&endDate=" + endDate+"&fileName="+cosog.string.bananceRecordExcel;
    openExcelWindow(context + '/balanceRecordController/exportExcelBalanceRecordData?flag=true&heads=' + param);
}