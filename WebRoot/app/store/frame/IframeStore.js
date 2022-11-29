var orgIframeSelectedRecord=null;
Ext.define('AP.store.frame.IframeStore', {
    extend: 'Ext.data.TreeStore',
    storeId: 'IframeStore_ids',
    folderSort: true,
    proxy: {
        type: 'ajax',
        actionMethods: 'post',
        url: context + '/orgManagerController/constructOrgTree',
        reader: {
            type: 'json',
            keepRawData: true
        }
    },
    listeners: {
        load: function (store, options, eOpts) {
        	var selectresult = [];
        	var selectReeTextRsult = [];
        	for(var i=0;i<store.data.items.length;i++){
        		selectresult.push(store.data.items[i].data.orgId);
        		selectReeTextRsult.push(""+store.data.items[i].data.text+"");
        	}
        	var org_Id = selectresult.join(",");
        	var org_Name=selectReeTextRsult.join(",");
        	
        	Ext.getCmp("leftOrg_Id").setValue(""); // 将org_Id的值赋值给IframeView里的组织隐藏域
            Ext.getCmp("leftOrg_Id").setValue(org_Id); // 将org_Id的值赋值给IframeView里的组织隐藏域
            Ext.getCmp("leftOrg_Name").setValue("");// 将org_Id的值赋值给IframeView里的组织隐藏域
    		Ext.getCmp("leftOrg_Name").setValue(org_Name);// 将org_Id的值赋值给IframeView里的组织隐藏域
    		
    		if(orgIframeSelectedRecord!=null){
    			for(var i=0;i<store.data.items.length;i++){
        			if(orgIframeSelectedRecord==store.data.items[i].data.orgId){
        				Ext.getCmp("IframeView_Id").getSelectionModel().select(i,true);
        				break;
        			}
        		}
    		}else{
    			var tabPanel = Ext.getCmp("frame_center_ids");
    			if(tabPanel.activeTab!=undefined){
    				var activeId = tabPanel.getActiveTab().id;
        			if(activeId=="OrganizationAndUserManagement"){
        				Ext.getCmp("OrgInfoTreeGridView_Id").getStore().load();
        			}
    			}
    			
    		}
        },
        beforeload: function (store, options) {
        	orgIframeSelectedRecord=null;
        	var treePanel=Ext.getCmp("IframeView_Id");
        	if(isNotVal(treePanel)){
        		var selectedOrg = treePanel.getSelectionModel().getSelection();
            	if(selectedOrg.length>0){
            		orgIframeSelectedRecord=selectedOrg[0].data.orgId;
            	}
        	}
        	
        }
    }
});