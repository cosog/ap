Ext.define("AP.view.acquisitionUnit.CellColorSelectWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.CellColorSelectWindow',
    layout: 'fit',
    title:loginUserLanguageResource.colorSelect,
    iframe: true,
    id: 'CellColorSelectWindow_Id',
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
				id : 'cellColorSelectedTableType_Id',
				value:0
			},{
				xtype : "hidden",
				id : 'cellColorSelectedRow_Id',
				value:-1
			},{
				xtype : "hidden",
				id : 'cellColorSelectedCol_Id',
				value:-1
			},{
        		id: 'CellColorSelectWindowColor_id',
        		xtype: 'colorselector',
        		anchor:'100%'
        	}],
            buttons: [{
            	xtype: 'button',
            	text: '确认',
                iconCls: 'save',
                handler: function () {
                	var cellColor=Ext.getCmp('CellColorSelectWindowColor_id').getValue();
                	var row=Ext.getCmp('cellColorSelectedRow_Id').getValue();
                	var col=Ext.getCmp('cellColorSelectedCol_Id').getValue();
                	var tableType=Ext.getCmp('cellColorSelectedTableType_Id').getValue();
                	if(parseInt(row)>=0 && parseInt(col)>=0){
                		if(tableType==231){//显示单元 采控项列表
                			protocolDisplayUnitAcqItemsConfigHandsontableHelper.hot.setDataAtCell(parseInt(row),parseInt(col),cellColor);
                		}else if(tableType==233){//显示单元 控制项列表
                			protocolDisplayUnitCtrlItemsConfigHandsontableHelper.hot.setDataAtCell(parseInt(row),parseInt(col),cellColor);
                		}
                	}
                	Ext.getCmp("CellColorSelectWindow_Id").close();
                }
            }]
        });
        Ext.apply(me, {
            items: postalarmUnitEditForm
        });
        me.callParent(arguments);
    }

});