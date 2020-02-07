Ext.define('AP.view.welllist.WellListInfoPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.wellListInfoPanel',
    id: "WellListInfo_Allpanel_Id",
    layout: "fit",
    border: false,
    viewConfig: {
        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
        forceFit: true
    },
    selType: 'checkboxmodel',
    multiSelect: false,
    initComponent: function () {
        var welllist_Store = Ext.create("AP.store.welllist.WellListStore");
        this.callParent(arguments)
    }
});
