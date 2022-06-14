Ext.define("AP.view.realTimeMonitoring.ResourceProbeHistoryCurveWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.resourceProbeHistoryCurveWindow',
    layout: 'fit',
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
//    constrain: true,
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
    width: 900,
    minWidth: 500,
    height: 350,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.apply(me, {
        	tbar:[
        		{
                    xtype: 'datefield',
                    anchor: '100%',
                    hidden: true,
                    fieldLabel: cosog.string.startDate,
                    labelWidth: 58,
                    width: 178,
                    format: 'Y-m-d ',
                    id: 'ResourceProbeHistoryCurve_from_date_Id',
                    value: 'new',
                    listeners: {
                    	select: function (combo, record, index) {
                    		Ext.create("AP.store.realTimeMonitoring.ResourceProbeHistoryCurveStore");
                        }
                    }
                }, {
                    xtype: 'datefield',
                    anchor: '100%',
                    hidden: true,
                    fieldLabel: cosog.string.endDate,
                    labelWidth: 58,
                    width: 178,
                    format: 'Y-m-d',
                    id: 'ResourceProbeHistoryCurve_end_date_Id',
                    value: new Date(),
                    listeners: {
                    	select: function (combo, record, index) {
                    		Ext.create("AP.store.realTimeMonitoring.ResourceProbeHistoryCurveStore");
                        }
                    }
                }
        	],
        	html: '<div id="ResourceProbeHistoryCurveDiv_Id" style="width:100%;height:100%;"></div>',
            listeners: {
                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    if ($("#ResourceProbeHistoryCurveDiv_Id").highcharts() != undefined) {
                        $("#ResourceProbeHistoryCurveDiv_Id").highcharts().setSize($("#ResourceProbeHistoryCurveDiv_Id").offsetWidth, $("#ResourceProbeHistoryCurveDiv_Id").offsetHeight, true);
                    }else{
                    	Ext.create("AP.store.realTimeMonitoring.ResourceProbeHistoryCurveStore");
                    }
                },
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});