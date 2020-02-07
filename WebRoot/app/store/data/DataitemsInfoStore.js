Ext.define('AP.store.data.DataitemsInfoStore',{
	extend:'Ext.data.Store',
	storeId:"DataitemsInfoStoreId",
	id:"DataitemsInfoStoreId",
	alias : 'widget.dataitemsInfoStore',
	pageSize:defaultPageSize, 
	model:'AP.model.data.DataitemsInfoModel',
	autoLoad:true,
    proxy: {
        type: 'ajax', 
        url : context+'/dataitemsInfoController/findDataitemsInfoByListId',       
        actionMethods : {
			read : 'POST'
		},
		start:0,
		limit:defaultPageSize,	 
        reader: {
            type: 'json',
            rootProperty: 'totalRoot', 
            totalProperty:'totalCount',
            keepRawData: true
        }
    }, 
    listeners: {
        beforeload: function(store, options) {	  
		        var getsysId= Ext.getCmp('sys_txt_find_ids').getValue(); 
		        var getcomoxbId= Ext.getCmp('findtattxtcobmoxfield_Id').getValue(); 
		        var gettxtcobcmoId= Ext.getCmp('findtattxtnames_Id'); 
		        if(!Ext.isEmpty(gettxtcobcmoId)){
		            gettxtcobcmoId=gettxtcobcmoId.getValue(); 
		        }
	        	var new_params = {
	        	        sysId:getsysId,
	        	        dataTabId:getcomoxbId,
	        	        dataName:gettxtcobcmoId
	        	};
	            Ext.apply(store.proxy.extraParams, new_params); 
        }
    }
});