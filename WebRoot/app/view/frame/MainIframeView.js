Ext.define('AP.view.frame.MainIframeView', {
			extend : 'Ext.tree.Panel',
			id : 'MainIframeView_Id',
			alias : 'widget.mainIframeView',
			border : false,
			viewConfig : {
				loadMask : true
			},
			layout : 'fit',
			enableDD : false,
			useArrows : true,
			rootVisible : false,
			autoScroll : true,
			animate : true,
			initComponent : function() {
				var moduleTree = this;
				var mainIframe_store;
//				if(userSyncOrAsync=="1"){
//					mainIframe_store = Ext.create("AP.store.frame.MainIframeStore2");
//				}else{
//					mainIframe_store = Ext.create("AP.store.frame.MainIframeStore");
//				}
				mainIframe_store = Ext.create("AP.store.frame.MainIframeStore");
				Ext.apply(moduleTree, {
							store : mainIframe_store,
							tbar : {
								hidden: true,
								items : [{
											iconCls : 'icon-collapse-all', // 收缩按钮
											text : '收缩',
											tooltip : {
												text : '收缩全部'
											},
											handler : function() {
												moduleTree.collapseAll();
											}
										}, '-', {
											iconCls : 'icon-expand-all',// 展开按钮
											tooltip : {
												text : '展开全部'
											},
											text : '展开',
											handler : function() {
												moduleTree.expandAll();
											}
										}]
							}

						});
				this.callParent(arguments);
			}

		});
