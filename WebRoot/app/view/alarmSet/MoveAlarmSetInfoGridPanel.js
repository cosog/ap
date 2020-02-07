Ext.define('AP.view.alarmSet.MoveAlarmSetInfoGridPanel', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.moveAlarmSetInfoGridPanel',
	id : "MoveAlarmSetInfoGridPanelView_Id",
	layout : "fit",
	border : false,
	initComponent : function() {
		var MoveAlarmSetGridInfoStore = Ext.create("AP.store.alarmSet.MoveAlarmSetGridInfoStore");
		Ext.apply(this, {
					tbar : ['->', {
								xtype : 'button',
								hidden:true,
								itemId : 'addMoveAlarmSetLabelClassBtnId',
								id : 'addMoveAlarmSetLabelClassBtn_Id',
								action : 'addMoveAlarmSetAction',
								text :  cosog.string.add,
								iconCls : 'add',
								handler:function(){
									addMoveAlarmSet();
								}
							},"-", {
								xtype : 'button',
								itemId : 'editMoveAlarmSetLabelClassBtnId',
								id : 'editMoveAlarmSetLabelClassBtn_Id',
								text :  cosog.string.update,
								action : 'editMoveAlarmSetInfoAction',
								disabled : true,
								iconCls : 'edit',
								handler:function(){
									modifyMoveAlarmSet();
								}
							}, "-", {
								xtype : 'button',
								hidden:true,
								itemId : 'delMoveAlarmSetLabelClassBtnId',
								id : 'delMoveAlarmSetLabelClassBtn_Id',
								disabled : true,
								action : 'delMoveAlarmSetAction',
								text :  cosog.string.del,
								iconCls : 'delete',
								handler:function(){
									delectMoveAlarmSet();
								}
							}]
				});

		this.callParent(arguments);

	}
});
