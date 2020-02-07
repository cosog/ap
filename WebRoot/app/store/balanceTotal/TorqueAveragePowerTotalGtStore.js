Ext.define('AP.store.balanceTotal.TorqueAveragePowerTotalGtStore', {
    extend: 'Ext.data.Store',
    id: 'TorqueAveragePowerTotalGtStore_Id',
    alias: 'widget.torqueAveragePowerTotalGtStore',
    model: 'AP.model.graphical.X_Y_Model',
    autoLoad: true,
    pageSize: defaultGraghSize,
    proxy: {
        type: 'ajax',
        url: context + '/balanceTotalAnalysisController/getTotalSurfaceCardData',
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
        	Ext.getCmp("TorqueAveragePowerTotalGtPanel_Id").unmask(cosog.string.loading); // 数据加载中，请稍后
            var get_rawData = store.proxy.reader.rawData; // 获取store数据
//            Ext.getCmp("TorqueAveragePowerTotalEndDate_Id").setValue(get_rawData.endDate);
            var totals = get_rawData.totals; // 总记录数
            var totalPages = get_rawData.totalPages; // 总页数
            Ext.getCmp("TorqueAveragePowerTotalGtTotalPages_Id").setValue("");
            Ext.getCmp("TorqueAveragePowerTotalGtTotalPages_Id").setValue(totalPages);
            var graphicalList = get_rawData.list; // 获取功图数据
            
            var TorqueAveragePowerTotalGtPanel = Ext.getCmp("TorqueAveragePowerTotalGtPanel_Id"); // 获取功图列表panel信息
            var panelHeight = TorqueAveragePowerTotalGtPanel.getHeight(); // panel的高度
            var panelWidth = TorqueAveragePowerTotalGtPanel.getWidth(); // panel的宽度
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
            $("#TorqueAveragePowerTotalGtDiv_Id").html(''); // 将html内容清空
            var htmlResult = '';
            var divId = '';

            if (totals > 0) {
                // 功图列表，创建div
                Ext.Array.each(graphicalList, function (name, index, countriesItSelf) {
                    divId = 'TorqueAveragePowerTotalGt' + graphicalList[index].id;
                    htmlResult += '<div id=\"' + divId + '\"';
                    htmlResult += ' style="height:'+ gtHeight2 +';width:'+ gtWidth2 +';"';
                    htmlResult += '></div>';
                });
                $("#TorqueAveragePowerTotalGtDiv_Id").append(htmlResult);
                Ext.Array.each(graphicalList, function (name, index, countriesItSelf) {
                    divId = 'TorqueAveragePowerTotalGt' + graphicalList[index].id;
                    showBalanceCycleSurfaceCardChart(graphicalList[index], divId,Ext.getCmp("TorqueAveragePowerTotalAnalysisiDataGrid_Id").getSelectionModel().getSelection()[0].data.jh); // 调用画功图的函数，功图列表
                });
            }
        },
        beforeload: function (store, options) {
            Ext.getCmp("TorqueAveragePowerTotalGtPanel_Id").mask(cosog.string.loading); // 数据加载中，请稍后
            var leftOrg_Id = Ext.getCmp('leftOrg_Id').getValue();
            var end_date = Ext.getCmp("TorqueAveragePowerTotalAnalysisiDataGrid_Id").getSelectionModel().getSelection()[0].data.jssj;
            var jh =Ext.getCmp("TorqueAveragePowerTotalAnalysisiDataGrid_Id").getSelectionModel().getSelection()[0].data.jh;
            var new_params = {
                orgId: leftOrg_Id,
                jh: jh,
                endDate: end_date,
                type:3
            };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
//            onStoreSizeChange(v, o, "TorqueAveragePowerTotalGtCount_Id"); //获取总记录数
        }
    }
});