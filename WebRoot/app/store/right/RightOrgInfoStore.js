Ext.define('AP.store.right.RightOrgInfoStore', {
    extend: 'Ext.data.TreeStore',
    storeId: 'RightOrgInfoStore_ids',
    folderSort: true,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: context + '/orgManagerController/constructOrgRightTree',
        reader: {
            type: 'json',
            keepRawData: true
        }
    },
    listeners: {
        load: function (store, options, eOpts) {
//            alert(store);
//        	var RightOrgInfoView_Id = Ext.getCmp("RightOrgInfoView_Id");
//        	if (!isNotVal(RightOrgInfoView_Id)) {
//        		RightOrgInfoView_Id = Ext.create('Ext.tree.Panel', {
//        			id: 'RightOrgInfoView_Id',
//        		    alias: 'widget.rightOrgInfoView',
//        		    store:store,
//        		    border: false,
//        		    forceFit: true,
//        		    viewConfig: {
//        		        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + cosog.string.nodata + "></div>",
//        		        forceFit: true
//        		    },
//        		    layout: 'fit',
//        		    //enableDD : false,
//        		    useArrows: true,
//        		    rootVisible: false,
//        		    autoScroll: true,
//        		    animate: true
//        		});
//        		Ext.getCmp("RightOrgInfoViewPanel_Id").add(RightOrgInfoView_Id);
//        	}
        }
    }
});