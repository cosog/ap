module_add = "";
module_modify = "";
module_delete = "";
moduleIndex_ = [];
Ext.define('AP.view.role.RightModuleInfoTreeGridView', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.rightModuleInfoTreeGridView',
    layout: "fit",
    border: false,
    animate: true,
    enableDD: false,
    useArrows: false,
    rootVisible: false,
    autoScroll: true,
    forceFit: true,
    id: "RightModuleTreeInfoGridPanel_Id", // 模块编码加id，定义的命名规则moduleCode是从库里取的值对应
    viewConfig: {
        emptyText: "<div class='con_div_' id='div_lcla_bjgid'><" + loginUserLanguageResource.emptyMsg + "></div>",
        forceFit: true
    },
    initComponent: function () {
        var moduleTree = this;
        var loginUserRoleModules=getLoginUserRoleModules();
        var moduleStore = Ext.create("AP.store.role.RightModuleTreeInfoStore");
        Ext.apply(moduleTree, {
            store: moduleStore,
            viewConfig: {　　
            	markDirty: false//编辑不显示红色三角标志
            },
            columns: [{
            	xtype: 'treecolumn',
            	text: '模块列表',
            	flex: 8,
            	align: 'left',
            	dataIndex: 'text'
            },{
            	header: 'mdIdaa',
            	hidden: true,
            	dataIndex: 'mdId'
            }, {
                header: '浏览',
                xtype: 'checkcolumn',
                lockable: true,
                align: 'center',
                sortable: true,
                flex: 1,
                dataIndex: 'viewFlagName',
//                disabled: true,
                editor: {
                	xtype: 'checkbox',
                    cls: 'x-grid-checkheader-editor',
                	allowBlank: false
                },
                renderer: function (value, metaData, record, rowIdx, colIdx, store, view) {
                    var rn=true;
                    var RoleManagerModuleEditFlag=parseInt(Ext.getCmp("RoleManagerModuleEditFlag").getValue());
                    if(RoleManagerModuleEditFlag==1){
        	    		for(var i=0;i<loginUserRoleModules.length;i++){
        	    			if(loginUserRoleModules[i].mdCode==record.data.mdCode){
        	    				if(loginUserRoleModules[i].viewFlag!=1){
        	    					rn= false;
        	    				}
        	    				break;
        	    			}
        	    		}
                    }else{
                    	rn= false;
                    }
                    return rn ? this.defaultRenderer(value, metaData):'';
                },
            	listeners: {
            		beforecheckchange: function( cell, rowIndex, checked, record, e, eOpts){
            			
            		}
            	}
            }, {
                header: '编辑',
                xtype: 'checkcolumn',
                lockable: true,
                align: 'center',
                sortable: true,
                flex: 1,
                dataIndex: 'editFlagName',
                editor: {
                	xtype: 'checkbox',
                    cls: 'x-grid-checkheader-editor',
                	allowBlank: false
                },
                renderer: function (value, metaData, record, rowIdx, colIdx, store, view) {
                    var rn=true;
//                    var loginUserRoleModules=getLoginUserRoleModules();
        	    	var RoleManagerModuleEditFlag=parseInt(Ext.getCmp("RoleManagerModuleEditFlag").getValue());
                    if(RoleManagerModuleEditFlag==1){
                    	if(!record.isLeaf()){
                    		rn= false;
            	    	}else{
            	    		rn=true;
            	    		for(var i=0;i<loginUserRoleModules.length;i++){
            	    			if(loginUserRoleModules[i].mdCode==record.data.mdCode){
            	    				if(loginUserRoleModules[i].editFlag!=1){
            	    					rn= false;
            	    				}
            	    				break;
            	    			}
            	    		}
            	    	}
                    }else{
                    	rn= false;
                    }
                    return rn ? this.defaultRenderer(value, metaData):'';
                },
            	listeners: {
            	    beforecheckchange: function( cell, rowIndex, checked, record, e, eOpts){
            	    	
            	    }
            	}
            }, {
                header: '控制',
                xtype: 'checkcolumn',
                lockable: true,
                align: 'center',
                sortable: true,
                flex: 1,
                dataIndex: 'controlFlagName',
                editor: {
                	xtype: 'checkbox',
                    cls: 'x-grid-checkheader-editor',
                	allowBlank: false
                },
                renderer: function (value, metaData, record, rowIdx, colIdx, store, view) {
                    var rn=true;
//                    var loginUserRoleModules=getLoginUserRoleModules();
                    var RoleManagerModuleEditFlag=parseInt(Ext.getCmp("RoleManagerModuleEditFlag").getValue());
                    if(RoleManagerModuleEditFlag==1){
                    	if(record.data.mdCode.toUpperCase()!='DeviceRealTimeMonitoring'.toUpperCase()){
                    		rn= false;
            	    	}else{
            	    		rn=true;
            	    		for(var i=0;i<loginUserRoleModules.length;i++){
            	    			if(loginUserRoleModules[i].mdCode==record.data.mdCode){
            	    				if(loginUserRoleModules[i].controlFlag!=1){
            	    					rn= false;
            	    				}
            	    				break;
            	    			}
            	    		}
            	    	}
                    }else{
                    	rn= false;
                    }
                    return rn ? this.defaultRenderer(value, metaData):'';
                },
            	listeners: {
            	    beforecheckchange: function( cell, rowIndex, checked, record, e, eOpts){
            	    	
            	    }
            	}
            }]
        });
        this.callParent(arguments);
    },
    listeners: {
        checkchange: function (node, checked) {
            listenerCheck(node, checked);
        }
    }
});

function getLoginUserRoleModules(){
	var loginUserRoleModules=[];
	Ext.Ajax.request({
		method:'POST',
		async: false,
		url:context + '/roleManagerController/getLoginUserRoleModules',
		success:function(response) {
			loginUserRoleModules = Ext.JSON.decode(response.responseText);
		},
		failure:function(){
		}
	});
	return loginUserRoleModules;
}