Ext.define('AP.store.graphicalQuery.SurfaceCardStore', {
    extend: 'Ext.data.Store',
    id: 'SurfaceCardStore_Id',
    alias: 'widget.SurfaceCardStore',
    model: 'AP.model.graphical.X_Y_Model',
    autoLoad: true,
    pageSize: defaultGraghSize,
    proxy: {
        type: 'ajax',
        url: context + '/surfaceCardQueryController/querySurfaceCard',
        actionMethods: {
            read: 'POST'
        },
        reader: {
            type: 'json',
            rootProperty: 'list',
            totalProperty: 'totals',
            keepRawData:true
        }
    },
    listeners: {
        load: function (store, options, eOpts) {
        	Ext.getCmp("SurfaceCardQuery_Id").unmask(cosog.string.loading); // 数据加载中，请稍后
            var get_rawData = store.proxy.reader.rawData; // 获取store数据
            var totals = get_rawData.totals; // 总记录数
            var totalPages = get_rawData.totalPages; // 总页数
            Ext.getCmp("SurfaceCardTotalPages_Id").setValue("");
            Ext.getCmp("SurfaceCardTotalPages_Id").setValue(totalPages);
            var gtlist = get_rawData.list; // 获取功图数据
            
            var surfaceCardContent = Ext.getCmp("surfaceCardContent"); // 获取功图列表panel信息
            var panelHeight = surfaceCardContent.getHeight(); // panel的高度
            var panelWidth = surfaceCardContent.getWidth(); // panel的宽度
            var scrollWidth = 0; // 默认无滚动条
            var columnCount = parseInt( panelWidth / graghMinWidth); // 无滚动条时一行显示的图形个数，graghMinWidth定义在CommUtils.js
            var rowCount = (totals%columnCount==0)?(totals/columnCount):(totals/columnCount+1); // 无滚动时总行数
            var gtWidth = panelWidth / columnCount; // 无滚动条时图形宽度
            var gtHeight = gtWidth * 0.75; // 无滚动条时图形高度
            var tatolHeight = gtHeight * rowCount; // 无滚动条时的图形总高度
            if( tatolHeight > panelHeight ){ // 判断有无滚动条
                scrollWidth = getScrollWidth(); // 滚动条的宽度
                columnCount = parseInt( (panelWidth - scrollWidth) / graghMinWidth); // 有滚动条时一行显示的图形个数
                gtWidth = (panelWidth - scrollWidth) / columnCount; // 有滚动条时图形宽度
                gtHeight = gtWidth * 0.75; // 有滚动条时图形高度
            }
            var gtWidth2 = gtWidth + 'px';
            var gtHeight2 = gtHeight + 'px';
            $("#surfaceCardContainer").html(''); // 将html内容清空
            var htmlResult = '';
            var divId = '';

            if (totals > 0) {
                // 功图列表，创建div
                Ext.Array.each(gtlist, function (name, index, countriesItSelf) {
                    divId = 'gt' + gtlist[index].id;
                    htmlResult += '<div id=\"' + divId + '\"';
                    htmlResult += ' style="height:'+ gtHeight2 +';width:'+ gtWidth2 +';"';
                    htmlResult += '></div>';
                });
                $("#surfaceCardContainer").append(htmlResult);
                Ext.Array.each(gtlist, function (name, index, countriesItSelf) {
                    divId = 'gt' + gtlist[index].id;
                    showSurfaceCard(gtlist[index], divId); // 调用画功图的函数，功图列表
                });
            }
        },
        beforeload: function (store, options) {
            var task = new Ext.util.DelayedTask(function () {
//                            	LoadingWin("正在加载数据");
            });
            task.delay(100);
            Ext.getCmp("SurfaceCardQuery_Id").mask(cosog.string.loading); // 数据加载中，请稍后
            var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
            var wellName = Ext.getCmp('FSDiagramAnalysisGraphicalQueryWellName_Id').getValue();
            var start_date = Ext.getCmp("SurfaceCard_from_date_Id").rawValue;
            var end_date = Ext.getCmp("SurfaceCard_to_date_Id").rawValue;

           
            var new_params = {
                orgId: leftOrg_Id,
                wellName: wellName,
                startDate: start_date,
                endDate: end_date
            };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            onStoreSizeChange(v, o, "SurfaceCardTotalCount_Id"); //获取总记录数
        }
    }
});