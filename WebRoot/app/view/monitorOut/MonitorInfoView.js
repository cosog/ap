/*******************************************************************************
 * 定义动态监测采出井的视图
 *
 * @author gao
 *
 */

Ext.define("AP.view.monitorOut.MonitorInfoView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.monitorInfoView', // 定义别名
    layout: 'fit',
    border: false,
    initComponent: function () {
        var me = this;
        var monitorOutWellpanel = Ext.create('AP.view.monitorOut.MonitorPumpingUnitPanel');
        Ext.apply(me, {
            items: [{
                id: 'MonitorTab_Id', // 定义一个tab面板用来存放抽油机、自喷等tab。
                xtype: 'tabpanel',
                activeTab: 0,
                border: false,
                tabPosition: 'bottom', // 表示该tab位于底部,如果想让tab位于顶部，修改为top即可
                items: [{
                        title: cosog.string.pumpUnit,
                        layout: "border",
                        id: 'MonitorPumpingUnit',
                        border: false,
                        items: [{
                        	region: 'center',
                        	border: false,
                        	layout: 'fit',
                        	hidden:false,
                        	collapsible: true, // 是否折叠
                            split: true, // 竖折叠条
                            html:'<div id="MonitorCalculateMapDiv_Id" style="width:100%;height:100%;-webket-flex:3;-moz-flex:3;-ms-flex:3;-o-flex:3;flex:3;-webkit-display:flex;-webkit-flex-direction: column;-moz-display:flex;-moz-flex-direction: column;-ms-display:flex;-ms-flex-direction: column;-o-display:flex;-o-flex-direction: column;display:flex;flex-direction: column;margin: 0px;padding: 0px;"></div>',
                            listeners: {
                                resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                                	getCustomMapFrontDataAndShowMap(2,"MonitorCalculateMapDiv_Id");
                                }
                            }
                        },{
                        	region: 'west',
                        	width:'100%',
                        	layout: 'fit',
                        	collapsible: true, // 是否折叠
                            split: true, // 竖折叠条
                        	items:[monitorOutWellpanel]
                        }]
              }
             ]
     }]
        });
        me.callParent(arguments);
    }

});