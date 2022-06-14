Ext.define("AP.view.acquisitionUnit.CurveColorSelectWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.CurveColorSelectWindow',
    layout: 'fit',
    title:'曲线颜色选择',
    iframe: true,
    id: 'CurveColorSelectWindow_Id',
    closeAction: 'destroy',
    width: 700,
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
        var postalarmUnitEditForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            defaultType: 'colorfield',
            items: [{
				xtype : "hidden",
				id : 'curveColorSelectedTableType_Id',
				value:0
			},{
				xtype : "hidden",
				id : 'curveColorSelectedRow_Id',
				value:-1
			},{
				xtype : "hidden",
				id : 'curveColorSelectedCol_Id',
				value:-1
			},{
        		id: 'CurveColorSelectWindowColor_id',
        		xtype: 'colorselector',
        		anchor:'100%'
        	}],
            buttons: [{
            	xtype: 'button',
            	text: '确认',
                iconCls: 'save',
                handler: function () {
                	var curveColor=Ext.getCmp('CurveColorSelectWindowColor_id').getValue();
                	var row=Ext.getCmp('curveColorSelectedRow_Id').getValue();
                	var col=Ext.getCmp('curveColorSelectedCol_Id').getValue();
                	var tableType=Ext.getCmp('curveColorSelectedTableType_Id').getValue();
                	if(parseInt(row)>=0 && parseInt(col)>=0){
                		if(tableType==0){
                			protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.setDataAtCell(parseInt(row),parseInt(col),curveColor);
                		}else if(tableType==1){
                			protocolDisplayUnitCalItemsConfigHandsontableHelper.hot.setDataAtCell(parseInt(row),parseInt(col),curveColor);
                		}
                		
//                		protocolAcqUnitConfigItemsHandsontableHelper.hot.setDataAtCell(parseInt(row),parseInt(col),curveColor);
                	}
                	Ext.getCmp("CurveColorSelectWindow_Id").close();
                }
            }]
        });
        Ext.apply(me, {
            items: postalarmUnitEditForm
        });
        me.callParent(arguments);
    }

});