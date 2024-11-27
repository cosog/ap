Ext.define('AP.view.alarmSet.AlarmSetInfoGridPanel', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.alarmSetInfoGridPanel',
	id : "AlarmSetInfoGridPanelView_Id",
	layout : "fit",
	border : false,
	initComponent : function() {
		var AlarmSetGridInfoStore = Ext.create("AP.store.alarmSet.AlarmSetGridInfoStore");
		Ext.apply(this, {
					tbar : [{
                    	xtype:'label',
                    	text:loginUserLanguageResource.alarmItemConfig
                    },'->', {
								xtype : 'button',
								hidden:true,
								itemId : 'addAlarmSetLabelClassBtnId',
								id : 'addAlarmSetLabelClassBtn_Id',
								action : 'addAlarmSetAction',
								text : loginUserLanguageResource.add,
								iconCls : 'add',
								handler:function(){
									addAlarmSet();
								}
							},"-", {
								xtype : 'button',
								itemId : 'editAlarmSetLabelClassBtnId',
								id : 'editAlarmSetLabelClassBtn_Id',
								text : loginUserLanguageResource.update,
								action : 'editAlarmSetInfoAction',
								disabled : true,
								iconCls : 'edit',
								handler:function(){
									modifyAlarmSet();
								}
							}, "-", {
								xtype : 'button',
								hidden:true,
								itemId : 'delAlarmSetLabelClassBtnId',
								id : 'delAlarmSetLabelClassBtn_Id',
								disabled : true,
								action : 'delAlarmSetAction',
								text : loginUserLanguageResource.deleteData,
								iconCls : 'delete',
								handler:function(){
									delectAlarmSet();
								}
							}]
				});

		this.callParent(arguments);
	}
});


