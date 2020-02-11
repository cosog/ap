Ext.QuickTips.init();

Ext.Loader.setConfig({
	enabled : true
});
Ext.application({
	// 定义整个应用的命名空间
	name : 'AP',
	// 该应用下所有的文件都是相对于该目录
	// The path to the directory which contains all application's
	// classes. This path will be registered via Ext.Loader.setPath for
	// the namespace specified in the name config.
	appFolder : context + '/app',
	// 应用初始化时，调用的controller
	controllers : [ 'frame.IframeControl', 'frame.MainIframeControl', 'right.RightOrgInfoControl', 'right.RightInfoControl',
        'alarmSet.MoveAlarmSetInfoController' ],
	launch : function() {
		Ext.tip.QuickTipManager.init();
	},
	// 应用初始化自动创建一个名为Viewport 的 View
	autoCreateViewport : true
});