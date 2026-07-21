Ext.define('AP.store.role.RoleInfoStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.roleInfoStore',
    model: 'AP.model.role.RoleInfoModel',
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/roleManagerController/doRoleShow',
        actionMethods: {
            read: 'POST'
        },
        start: 0,
        limit: defaultPageSize,
        reader: {
            type: 'json',
            rootProperty: 'totalRoot',
            totalProperty: 'totalCount',
            keepRawData: true
        }
    },
    listeners: {
        load: function (store, options, eOpts) {
            //获得列表数
            var get_rawData = store.proxy.reader.rawData;
            
            var showChineseName=get_rawData.showChineseName;
            var showEnglishName=get_rawData.showEnglishName;
            var showRussianName=get_rawData.showRussianName;
            
            var currentId=get_rawData.currentId;
            var currentLevel=get_rawData.currentLevel;
            var currentShowLevel=get_rawData.currentShowLevel;
            var currentFlag=get_rawData.currentFlag;
            var currentReportEdit=get_rawData.currentReportEdit;
            var currentVideoKeyEdit=get_rawData.currentVideoKeyEdit;
            var currentLanguageEdit=get_rawData.currentLanguageEdit;
            
            Ext.getCmp("currentUserRoleId_Id").setValue(currentId);
            Ext.getCmp("currentUserRoleLevel_Id").setValue(currentLevel);
            Ext.getCmp("currentUserRoleShowLevel_Id").setValue(currentShowLevel);
            Ext.getCmp("currentUserRoleVideoKeyEdit_Id").setValue(currentVideoKeyEdit);
            Ext.getCmp("currentUserRoleLanguageEdit_Id").setValue(currentLanguageEdit);
            var gridPanel = Ext.getCmp("RoleInfoGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var arrColumns = get_rawData.columns;
                var cloums = createRoleGridColumn(arrColumns);
                var newColumns = Ext.JSON.decode(cloums);
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "RoleInfoGridPanel_Id",
                    selModel: 'cellmodel',//cellmodel rowmodel
                    plugins: [{
                        ptype: 'cellediting',//cellediting rowediting
                        clicksToEdit: 2
                    }],
                    border: false,
                    stateful: true,
                    columnLines: true,
                    layout: "fit",
                    stripeRows: true,
                    forceFit: false,
                    selModel:{
                    	selType: (loginUserRoleManagerModuleRight.editFlag==1?'checkboxmodel':''),
                    	mode:'MULTI',//"SINGLE" / "SIMPLE" / "MULTI" 
                    	checkOnly:false,
                    	allowDeselect:false
                    },
                    viewConfig: {
                        emptyText: "<div class='con_div_' id='div_dataactiveid'>" + Ext.String.htmlEncode("<" + loginUserLanguageResource.emptyMsg + ">") + "</div>"
                    },
                    store: store,
                    columns: [{
                        header: loginUserLanguageResource.idx,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        width: getLabelWidth(loginUserLanguageResource.idx,loginUserLanguage)+'px',
                        xtype: 'rownumberer'
                    }, {
                        header: loginUserLanguageResource.language_zh_CN,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 1,
                        dataIndex: 'roleName_zh_CN',
                        hidden:!showChineseName,
                        editor: loginUserRoleManagerModuleRight.editFlag==1?{
                            allowBlank: true,
                            disabled:loginUserRoleManagerModuleRight.editFlag!=1
                        }:"",
                        renderer: function (value, o, p, e) {
                            return adviceCurrentRoleName(value, o, p, e);
                        }
                    }, {
                        header: loginUserLanguageResource.language_en,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 1,
                        dataIndex: 'roleName_en',
                        hidden:!showEnglishName,
                        editor: loginUserRoleManagerModuleRight.editFlag==1?{
                            allowBlank: true,
                            disabled:loginUserRoleManagerModuleRight.editFlag!=1
                        }:"",
                        renderer: function (value, o, p, e) {
                            return adviceCurrentRoleName(value, o, p, e);
                        }
                    }, {
                        header: loginUserLanguageResource.language_ru,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 1,
                        dataIndex: 'roleName_ru',
                        hidden:!showRussianName,
                        editor: loginUserRoleManagerModuleRight.editFlag==1?{
                            allowBlank: true,
                            disabled:loginUserRoleManagerModuleRight.editFlag!=1
                        }:"",
                        renderer: function (value, o, p, e) {
                            return adviceCurrentRoleName(value, o, p, e);
                        }
                    }, {
                        header: loginUserLanguageResource.roleLevel,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 1,
                        dataIndex: 'roleLevel',
                        editor: loginUserRoleManagerModuleRight.editFlag==1?{
                            allowBlank: false,
                            xtype: 'numberfield',
                            editable: false,
                            disabled:loginUserRoleManagerModuleRight.editFlag!=1,
                            minValue: currentLevel+1
                        }:""
                    }, {
                        header: loginUserLanguageResource.dataShowLevel,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 1,
                        dataIndex: 'showLevel',
                        editor: loginUserRoleManagerModuleRight.editFlag==1?{
                            allowBlank: false,
                            xtype: 'numberfield',
                            editable: false,
                            disabled:loginUserRoleManagerModuleRight.editFlag!=1,
                            minValue: currentShowLevel
                        }:""
                    }, {
                        header: loginUserLanguageResource.roleVideoKeyEdit,
                        xtype: 'checkcolumn',
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        disabled:loginUserRoleManagerModuleRight.editFlag!=1,
                        width: getLabelWidth(loginUserLanguageResource.roleVideoKeyEdit,loginUserLanguage)+'px',
                        dataIndex: 'roleVideoKeyEditName',
                        editor: {
                        	xtype: 'checkbox',
                            cls: 'x-grid-checkheader-editor',
                        	allowBlank: false
                        },
                    	listeners: {
                    	    beforecheckchange: function( cell, rowIndex, checked, record, e, eOpts){
                    	    	var RoleManagerModuleEditFlag=parseInt(Ext.getCmp("RoleManagerModuleEditFlag").getValue());
        	                    if(RoleManagerModuleEditFlag==1){
        	                    	var currentId=Ext.getCmp("currentUserRoleId_Id").getValue();
                        	    	var currentUserRoleVideoKeyEdit=Ext.getCmp("currentUserRoleVideoKeyEdit_Id").getValue();
                        	    	if(parseInt(record.data.roleId)==parseInt(currentId) || currentUserRoleVideoKeyEdit==0){
                        	    		return false;
                        	    	}else{
                                        return true;
                                    }
        	                    }else{
        	                    	return false;
        	                    }
                    	    }
                    	}
                    }, {
                        header: loginUserLanguageResource.roleLanguageEdit,
                        xtype: 'checkcolumn',
                        hidden: true,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        disabled:loginUserRoleManagerModuleRight.editFlag!=1,
                        width: getLabelWidth(loginUserLanguageResource.roleLanguageEdit,loginUserLanguage)+'px',
                        dataIndex: 'roleLanguageEditName',
                        editor: {
                        	xtype: 'checkbox',
                            cls: 'x-grid-checkheader-editor',
                        	allowBlank: false
                        },
                    	listeners: {
                    	    beforecheckchange: function( cell, rowIndex, checked, record, e, eOpts){
                    	    	var RoleManagerModuleEditFlag=parseInt(Ext.getCmp("RoleManagerModuleEditFlag").getValue());
        	                    if(RoleManagerModuleEditFlag==1){
        	                    	var currentId=Ext.getCmp("currentUserRoleId_Id").getValue();
                        	    	var currentUserRoleLanguageEdit=Ext.getCmp("currentUserRoleLanguageEdit_Id").getValue();
                        	    	if(parseInt(record.data.roleId)==parseInt(currentId) || currentUserRoleLanguageEdit==0){
                        	    		return false;
                        	    	}else{
                                        return true;
                                    }
        	                    }else{
        	                    	return false;
        	                    }
                    	    }
                    	}
                    },{
                        header: loginUserLanguageResource.roleRemark,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 3,
                        dataIndex: 'remark_zh_CN',
                        hidden:(loginUserLanguage.toUpperCase()=='ZH_CN'?false:true),
                        editor: loginUserRoleManagerModuleRight.editFlag==1?{
                        	allowBlank: true,
                        	disabled:loginUserRoleManagerModuleRight.editFlag!=1
                        }:"",
                        renderer: function (value) {
                            if (isNotVal(value)) {
                                return Ext.String.format('<span data-qtip="{0}">{0}</span>', Ext.String.htmlEncode(value));
                            }
                        }
                    },{
                        header: loginUserLanguageResource.roleRemark,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 3,
                        dataIndex: 'remark_en',
                        hidden:(loginUserLanguage.toUpperCase()=='EN'?false:true),
                        editor: loginUserRoleManagerModuleRight.editFlag==1?{
                        	allowBlank: true,
                        	disabled:loginUserRoleManagerModuleRight.editFlag!=1
                        }:"",
                        renderer: function (value) {
                            if (isNotVal(value)) {
                                return Ext.String.format('<span data-qtip="{0}">{0}</span>', Ext.String.htmlEncode(value));
                            }
                        }
                    },{
                        header: loginUserLanguageResource.roleRemark,
                        lockable: true,
                        align: 'center',
                        sortable: true,
                        flex: 3,
                        dataIndex: 'remark_ru',
                        hidden:(loginUserLanguage.toUpperCase()=='RU'?false:true),
                        editor: loginUserRoleManagerModuleRight.editFlag==1?{
                        	allowBlank: true,
                        	disabled:loginUserRoleManagerModuleRight.editFlag!=1
                        }:"",
                        renderer: function (value) {
                            if (isNotVal(value)) {
                                return Ext.String.format('<span data-qtip="{0}">{0}</span>', Ext.String.htmlEncode(value));
                            }
                        }
                    },{
                    	header: loginUserLanguageResource.save,
                    	xtype: 'actioncolumn',
                    	width: getLabelWidth(loginUserLanguageResource.save,loginUserLanguage)+'px',
                        align: 'center',
                        hidden: true,
                        sortable: false,
                        menuDisabled: true,
                        items: [{
                            iconCls: 'submit',
                            tooltip: loginUserLanguageResource.save,
                            disabled:loginUserRoleManagerModuleRight.editFlag!=1,
                            handler: function (view, recIndex, cellIndex, item, e, record) {
                            	var RoleManagerModuleEditFlag=parseInt(Ext.getCmp("RoleManagerModuleEditFlag").getValue());
        	                    if(RoleManagerModuleEditFlag==1){
        	                    	updateRoleInfoByGridBtn(record);
        	                    }
                            }
                        }]
                    },{
                    	header: loginUserLanguageResource.deleteData,
                    	xtype: 'actioncolumn',
                    	width: getLabelWidth(loginUserLanguageResource.deleteData,loginUserLanguage)+'px',
                        align: 'center',
                        hidden: true,
                        sortable: false,
                        menuDisabled: true,
                        items: [{
                            iconCls: 'delete',
                            tooltip: loginUserLanguageResource.deleteData,
                            disabled:loginUserRoleManagerModuleRight.editFlag!=1,
                            handler: function (view, recIndex, cellIndex, item, e, record) {
                            	var RoleManagerModuleEditFlag=parseInt(Ext.getCmp("RoleManagerModuleEditFlag").getValue());
        	                    if(RoleManagerModuleEditFlag==1){
        	                    	delRoleInfoByGridBtn(record);
        	                    }
                            }
                        }]
                    }],
                    listeners: {
                        selectionchange: function (sm, selected) {
                        	var roleId='';
                        	var roleCode='';
                        	var roleName='';
                        	if(selected.length>0){
                        		roleId = selected[0].data.roleId;
                        		roleCode = selected[0].data.roleCode;
                        		if(loginUserLanguage.toUpperCase()=='ZH_CN'){
                        			roleName = selected[0].data.roleName_zh_CN;
                        		}else if(loginUserLanguage.toUpperCase()=='EN'){
                        			roleName = selected[0].data.roleName_en;
                        		}else if(loginUserLanguage.toUpperCase()=='RU'){
                        			roleName = selected[0].data.roleName_ru;
                        		}
                        		
                        		Ext.getCmp("RightBottomRoleCodes_Id").setValue(roleId);
                        		
                        		Ext.getCmp("RightModuleTreeInfoLabel_Id").setHtml(loginUserLanguageResource.role+"【<font color='red'>"+roleName+"</font>】 "+loginUserLanguageResourceFirstLower.moduleLicense);
                        		clkLoadAjaxFn();
                        		
                        		Ext.getCmp("RightTabTreeInfoLabel_Id").setHtml(loginUserLanguageResource.role+"【<font color='red'>"+roleName+"</font>】 "+loginUserLanguageResourceFirstLower.deviceTypeLicense);
                        		clkLoadTabAjaxFn();
                        		
                        		Ext.getCmp("RightLanguageTreeInfoLabel_Id").setHtml(loginUserLanguageResource.role+"【<font color='red'>"+roleName+"</font>】 "+loginUserLanguageResourceFirstLower.languageLicense);
                        		clkLoadLanguageAjaxFn();
                        		
                        		var currentRoleId=Ext.getCmp("currentUserRoleId_Id").getValue();
                        		if(parseInt(currentRoleId)==parseInt(roleId)){//不能修改自己权限
                                    Ext.getCmp("RightModuleTreeInfoGridPanel_Id").disable();
                                    
                                    Ext.getCmp("roleGrantRightBtn_Id").disable();
                                                                        
                                    Ext.getCmp("addRightModuleLableClassBtn_Id").disable();
                                    
                                    Ext.getCmp("RightTabTreeInfoGridPanel_Id").disable();
                                    Ext.getCmp("addRightTabLableClassBtn_Id").disable();
                                    
                                    Ext.getCmp("RightLanguageTreeInfoGridPanel_Id").disable();
                                    Ext.getCmp("addRightLanguageLableClassBtn_Id").disable();
                                }else{
                                	Ext.getCmp("RightModuleTreeInfoGridPanel_Id").enable();
                                	Ext.getCmp("RightTabTreeInfoGridPanel_Id").enable();
                                	Ext.getCmp("RightLanguageTreeInfoGridPanel_Id").enable();
                                	
                                	var RoleManagerModuleEditFlag=parseInt(Ext.getCmp("RoleManagerModuleEditFlag").getValue());
            	                    if(RoleManagerModuleEditFlag==1){
            	                    	Ext.getCmp("roleGrantRightBtn_Id").enable();
            	                    	Ext.getCmp("addRightModuleLableClassBtn_Id").enable();
                                        Ext.getCmp("addRightTabLableClassBtn_Id").enable();
                                        Ext.getCmp("addRightLanguageLableClassBtn_Id").enable();
            	                    }else{
            	                    	Ext.getCmp("roleGrantRightBtn_Id").disable();
            	                    	Ext.getCmp("addRightModuleLableClassBtn_Id").disable();
                                        Ext.getCmp("addRightTabLableClassBtn_Id").disable();
                                        Ext.getCmp("addRightLanguageLableClassBtn_Id").disable();
            	                    }
                                }
                        	}else{
                        		Ext.getCmp("RightBottomRoleCodes_Id").setValue("");
                        	}
                        },
                        celldblclick: function ( grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
                        	var currentId=Ext.getCmp("currentUserRoleId_Id").getValue();
                        	var header = grid.getHeaderByCell(td);
                            var dataIndex = header.dataIndex;
                            if (parseInt(record.data.roleId)==parseInt(currentId) 
                            		&& ( dataIndex.toUpperCase()=='roleLevel'.toUpperCase() 
                            				|| dataIndex.toUpperCase()=='showLevel'.toUpperCase() 
                            				|| dataIndex.toUpperCase()=='roleVideoKeyEditName'.toUpperCase()
                            			)
                            	) {
                                return false;
                            }else {
                                return true;
                            }
                        },
                        select( v, record, index, eOpts ){
                        	Ext.getCmp("selectedRoleId_Id").setValue(record.data.roleId);
                        }
                    }
                });
                var panel = Ext.getCmp("RoleInfoGridPanelView_id");
                if(isNotVal(panel)){
                	panel.add(gridPanel);
                }
            }
            
            
            var selectedRow=0;
            if(get_rawData.totalCount>0){
            	var addRoleFlag=Ext.getCmp("addRoleFlag_Id").getValue();
            	if(isNotVal(addRoleFlag)){
            		Ext.getCmp("addRoleFlag_Id").setValue('');
            		for(var i=0;i<store.data.length;i++){
                		if( (loginUserLanguage.toUpperCase()=='ZH_CN' && store.getAt(i).data.roleName_zh_CN==addRoleFlag)
                				|| (loginUserLanguage.toUpperCase()=='EN' && store.getAt(i).data.roleName_en==addRoleFlag)
                				|| (loginUserLanguage.toUpperCase()=='RU' && store.getAt(i).data.roleName_ru==addRoleFlag)
                		){
                			selectedRow=i;
                			break;
                		}
                		
                	}
            	}else{
            		var selectedRoleId= Ext.getCmp("selectedRoleId_Id").getValue();
            		if(selectedRoleId>0){
            			for(var i=0;i<store.data.length;i++){
                    		if(selectedRoleId==store.getAt(i).data.roleId){
                    			selectedRow=i;
                    			break;
                    		}
                    	}
            		}
            	}
            }else{
            	Ext.getCmp("selectedRoleId_Id").setValue(0);
            }
            
            gridPanel.getSelectionModel().deselectAll(true);
            gridPanel.getSelectionModel().select(selectedRow, true);
        },
        beforeload: function (store, options) {
            var RoleName_Id = Ext.getCmp('RoleName_Id');

            if (!Ext.isEmpty(RoleName_Id)) {
                RoleName_Id = RoleName_Id.getValue();
            }
            var new_params = {
                roleName: RoleName_Id
            };
            Ext.apply(store.proxy.extraParams, new_params);
        }
    }
});