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
				mainIframe_store = Ext.create("AP.store.frame.MainIframeStore");
				Ext.apply(moduleTree, {
							store : mainIframe_store,
							tbar : {
								hidden: false,
								items : [{
											iconCls : 'icon-collapse-all', // 收缩按钮
											text : loginUserLanguageResource.collapse,
											tooltip : {
												text : loginUserLanguageResource.collapseAll
											},
											handler : function() {
												moduleTree.collapseAll();
											}
										}, '-', {
											iconCls : 'icon-expand-all',// 展开按钮
											tooltip : {
												text : loginUserLanguageResource.expandAll
											},
											text : loginUserLanguageResource.expand,
											handler : function() {
												moduleTree.expandAll();
											}
										}, '-', {
							                iconCls: 'note-refresh',
							                tooltip: {
							                    text: loginUserLanguageResource.refresh
							                },
							                handler: function () {
							                	moduleTree.getStore().load();
							                }
							         }]
							}

						});
				this.callParent(arguments);
			}

		});
