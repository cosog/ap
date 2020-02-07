 Ext.define('AP.view.wellTrajectory.WellTrajectoryInfoPanel', {
     extend: 'Ext.panel.Panel',
     alias: 'widget.welltrajectoryInfoPanel',
     layout: "fit",
     border: false,
     id: 'WellTrajectoryInfoPanelView_Id',
     initComponent: function () {
         var welltrajectoryInfoStore = Ext.create('AP.store.wellTrajectory.WellTrajectoryInfoStore');
         // 检索动态列表头及内容信息
         Ext.Ajax.request({
             method: 'POST',
             url: context + '/wellTrajectoryController/showWellTracjectoryInfo',
             success: function (response, opts) {
                 // 处理后json
                 var obj = Ext.decode(response.responseText);
                 var wellTrajectoryjh_Id = Ext.getCmp("wellTrajectoryjh_Id");
                 wellTrajectoryjh_Id.setValue(obj.jh);
                 wellTrajectoryjh_Id.setRawValue(obj.jh);
                 Ext.getCmp("WellTrajectoryadd_Id").setValue("");
                 Ext.getCmp("WellTrajectoryadd_Id").setValue(obj.jh);
             },
             failure: function (response, opts) {
                 Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
             }
         });
         
         var OrgTreeStore=Ext.create('Ext.data.TreeStore', {
         	autoLoad:false,
         	proxy : {
 				type : 'ajax',
 				actionMethods : 'post',
 				url : context+ '/orgManagerController/constructOrgTree2',
 				reader : {
 					type : 'json',
 					rootProperty : 'list'
 				},
 				extraParams : {  
                     tid : '0'  
                 } 
 			},
 			root:{
 				text:'组织导航',
 				expanded:true,
 				orgId:'0'
 			},
             listeners: {
             	nodebeforeexpand : function(node,eOpts) {
 					this.proxy.extraParams.tid = node.data.orgId;
 				}
             }
         });
         var orgComboxTree=Ext.create('AP.view.well.TreePicker',{
         	id:'WellTrajectoryorg_Id',
         	fieldLabel: cosog.string.orgName,
         	labelWidth: 60,
             width: 240,
             emptyText: cosog.string.chooseOrg,
             blankText: cosog.string.chooseOrg,
             displayField: 'text',
             autoScroll:true,
             forceSelection : true,// 只能选择下拉框里面的内容
             rootVisible: false,
             store:OrgTreeStore,
             listeners: {
                 expand: function (sm, selections) {
                 },
                 specialkey: function (field, e) {
                 },
                 select: function (picker,record,eOpts) {
                 	resComboxTree.setValue("");
                 	resComboxTree.setRawValue("");
                 	simpleCombo.clearValue();
                 	orgComboxTree.setValue(record.data.orgCode);
                 	orgComboxTree.setRawValue(record.data.text);
                 	welltrajectoryInfoStore.load();
                 }
             }
         });
        
         var ResTreeStore=Ext.create('Ext.data.TreeStore', {
         	autoLoad:false,
         	proxy : {
 				type : 'ajax',
 				actionMethods : 'post',
 				url: context + '/resManagerController/constructResTreeGridTree',
 				reader : {
 					type : 'json',
 					rootProperty : 'children'
 				},
 				extraParams : {  
                     tid : '0'  
                 } 
 			},
 			root:{
 				text:'区块',
 				expanded:true,
 				orgId:'0'
 			},
             listeners: {
             	nodebeforeexpand : function(node,eOpts) {
 					this.proxy.extraParams.tid = node.data.resId;
 				}
             }
         });
         
         var resComboxTree=Ext.create('AP.view.well.TreePicker',{
         	id:'WellTrajectoryres_Id',
         	fieldLabel: cosog.string.resName,
        	emptyText: cosog.string.checkRes,
        	blankText: cosog.string.checkRes,
         	labelWidth: 70,
             width: 250,
             displayField: 'text',
             autoScroll:true,
             forceSelection : true,// 只能选择下拉框里面的内容
             rootVisible: false,
             store:ResTreeStore,
             listeners: {
                 expand: function (sm, selections) {
                 },
                 specialkey: function (field, e) {
                 },
                 select: function (picker,record,eOpts) {
                 	simpleCombo.clearValue();
                 	resComboxTree.setValue(record.data.resCode);
                 	resComboxTree.setRawValue(record.data.text);
                 	welltrajectoryInfoStore.load();
                 }
             }
         });
         
       
         var jhStore = new Ext.data.JsonStore({
        	 pageSize:defaultJhComboxSize,
             fields: [{
                 name: "boxkey",
                 type: "string"
     }, {
                 name: "boxval",
                 type: "string"
     }],
             proxy: {
            	 url: context + '/wellInformationManagerController/queryWellInfoParams',
                 type: "ajax",
                 actionMethods: {
                     read: 'POST'
                 },
                 reader: {
                     type: 'json',
                     rootProperty: 'list',
                     totalProperty: 'totals'
                 }
             },
             autoLoad: false,
             listeners: {
                 beforeload: function (store, options) {
                     var t_tobj = Ext.getCmp('WellTrajectoryorg_Id');
                     var t_t_val = "";
                     if (!Ext.isEmpty(t_tobj)) {
                         t_t_val = t_tobj.getValue();
                     }
                     var WellTrajectoryres_Id = Ext.getCmp('WellTrajectoryres_Id');
                     if (!Ext.isEmpty(WellTrajectoryres_Id)) {
                         WellTrajectoryres_Id = WellTrajectoryres_Id.getValue();
                     }
                     var jh_tobj = Ext.getCmp('wellTrajectoryjh_Id');
 					 var jh_val = "";
 					 if (!Ext.isEmpty(jh_tobj)) {
 						 jh_val = jh_tobj.getValue();
 					 }
                     var new_params = {
                         type: 'jh',
                         orgCode: t_t_val,
                         resCode: WellTrajectoryres_Id,
                         jh:jh_val
                     };
                     Ext.apply(store.proxy.extraParams,new_params);
                 }
             }
         });
         var simpleCombo = Ext.create(
             'Ext.form.field.ComboBox', {
                 fieldLabel: cosog.string.jh,
                 id: "wellTrajectoryjh_Id",
                 labelWidth: 35,
                 width: 145,
                 labelAlign: 'left',
                 queryMode: 'remote',
                 typeAhead: true,
                 store: jhStore,
                 autoSelect: false,
                 editable: true,
                 triggerAction: 'all',
                 displayField: "boxval",
                 emptyText: cosog.string.all,
                 blankText: cosog.string.all,
                 valueField: "boxkey",
                 pageSize:comboxPagingStatus,
                 minChars:0,
                 listeners: {
                     expand: function (sm, selections) {
//                         simpleCombo.clearValue();
                         simpleCombo.getStore().loadPage(1); // 加载井下拉框的store
                     },
                     select: function (combo, record, index) {
                         try {
                        	 welltrajectoryInfoStore.load();
                         } catch (ex) {
                             Ext.Msg.alert(cosog.string.tips, cosog.string.fail);
                         }
                     },
                     afterRender: function (combo, o) {
                         if (jhStore.getTotalCount() > 0) {
                             var orgId = jhStore.data.items[0].data.boxkey;
                             var orgName = jhStore.data.items[0].data.boxval;
                             combo.setValue(orgId);
                             combo.setRawValue(orgName);
                         }
                     },
                     specialkey: function (field, e) {
                         onEnterKeyDownFN(field, e, 'welltrajectoryPanel_Id');
                     }
                 }
             });
         Ext.apply(this, {
             tbar: [orgComboxTree, resComboxTree, simpleCombo, {
                 xtype: 'button',
                 name: 'welltrajectoryNameBtn_Id',
                 text: cosog.string.search,
//                 hidden: true,
                 pressed: true,
                 iconCls: 'search',
                 handler: function () {
                     welltrajectoryInfoStore.load();
                 }
   }, '->', {
                 xtype: 'button',
                 itemId: 'addrewelltrajectoryLabelClassBtnId',
                 id: 'addrewelltrajectoryLabelClassBtn_Id',
                 action: 'addrewelltrajectoryAction',
                 text: cosog.string.add,
                 iconCls: 'add'
   }, "-", {
                 xtype: 'button',
                 itemId: 'editrewelltrajectoryLabelClassBtnId',
                 id: 'editrewelltrajectoryLabelClassBtn_Id',
                 text: cosog.string.update,
                 action: 'editrewelltrajectoryInfoAction',
                 disabled: false,
                 iconCls: 'edit'
   }, "-", {
                 xtype: 'button',
                 itemId: 'delrewelltrajectoryLabelClassBtnId',
                 id: 'delrewelltrajectoryLabelClassBtn_Id',
                 disabled: false,
                 action: 'delrewelltrajectoryAction',
                 text: cosog.string.del,
                 iconCls: 'delete'
   }]

         })
         this.callParent(arguments);
     }
 });