Ext.define("AP.view.org.OrgInfoWithInternetView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.orgInfoWithInternetView',
    layout: 'fit',
//    iframe: true,
    border: false,
    initComponent: function () {
        var me = this;                   //地图工具库返回来的操作对象
        var OrgInfoTreeGridView = Ext.create('AP.view.org.OrgInfoTreeGridView');
        Ext.apply(me, {
            //items: [OrgInfoTreeGridView]
        	items:[{
        		id:'orgMapPanel_Id',
                border : false,
                layout:'border',
                items: [
                        {
                    layout: 'fit',
                    region: 'north',
                    width: '100%',
                    height: '65%',
                    border: false,
                    collapsible: true,
                    html:'<div id="orgCoordSetDiv_Id" style="width:100%;height:100%;"></div>'
                    +'<div id="tog"><img id="mapSearchImg" src="'+context+'/scripts/map/image/hide.png" /></div>'
                    +'<div id="searchTop">'
                    +'<input id="txtSearch" type="text" onkeyup="OnBackMapSearchOrg()" />'
                    +'<input id="btnSearch" type="button" value="搜索" onclick="OnBackMapSearchOrg();" />'
                    +'</div>'
                    +'<div id="searchPanel">'
                    +'</div>',
                    listeners: {
                        resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                        	mapHelperOrg = MapHelper.createNew("orgCoordSetDiv_Id", m_DefaultPosition, m_BackDefaultZoomLevel, false, 1000);
                        	mapHelperOrg.setMinZoom(5);
                            //地图绑定移动、缩放事件
                        	mapHelperOrg.addEventListener(mapHelperOrg.getMap(), "moveend", function(moveendEvent){
                        		ShowBackMarker(mapHelperOrg,m_BackOrgData);
                            });
                        	mapHelperOrg.addEventListener(mapHelperOrg.getMap(), "zoomend", function(zoomendEvent){
                        		ShowBackMarker(mapHelperOrg,m_BackOrgData);
                            });
                            //给地图添加单击事件
                        	mapHelperOrg.addEventListener(mapHelperOrg.getMap(), "click",function(clickEvent){
                        		mapClicked(clickEvent,mapHelperOrg,m_BackOrgData);
                            });
                            //获取后台数据并创建覆盖物
                            SaveBackMapData(mapHelperOrg,"org",m_BackDefaultZoomLevel);
                            $('#tog img').click(function () {
                                if ($('#mapSearchImg').attr('src') == context+'/scripts/map/image/hide.png') {
                                    $('#mapSearchImg').attr('src', context+'/scripts/map/image/show.png');
                                    $('#searchTop').hide();
                                    $('#searchPanel').hide();
                                } else {
                                    $('#mapSearchImg').attr('src', context+'/scripts/map/image/hide.png');
                                    $('#searchTop').show();
                                    $('#searchPanel').show();
                                }
                            });
                        }
                    }
                },
                {
                    layout: 'fit',
                    region: 'center',
                    width: '100%',
                    collapsible: false,
                    split: true,
                    border: false,
                    items: [OrgInfoTreeGridView]
                }]
        	}]
        });
        me.callParent(arguments);
    }
});

function aa(a,b){
	alert("a="+a+",b="+b);
}