/**
 * The main application class. An instance of this class is created by app.js when it calls
 * Ext.application(). This is the ideal place to handle application launch and initialization
 * details.
 */
Ext.define('cosog.Application', {
    extend: 'Ext.app.Application',
    
    name: 'cosog',
    
    appFolder : context + '/app',
	// 应用初始化时，调用的controller
	controllers : [ 'frame.IframeControl', 'frame.MainIframeControl' ],

    stores: [
        // TODO: add global / shared stores here
    ],
    
    launch: function () {
        // TODO - Launch the application
    }
});
