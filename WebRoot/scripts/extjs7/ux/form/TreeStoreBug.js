/* 
 *钱邓水
 *Ext4 tree getStore bug
 */ 
//extends 重写treeStore 加载机智
Ext.override(Ext.data.TreeStore, { 
    load: function(options) { 
      options = options || {}; 
      options.params = options.params || {}; 
      var me = this, 
      node = options.node || me.tree.getRootNode(), 
      root; 
	// 如果是根，就让它展开
	// create one for them. 
	if (!node) { 
	 node = me.setRootNode({ 
	  expanded: true 
	 }); 
	} 
	if (me.clearOnLoad) { 
	  node.removeAll(false); 
	} 

	Ext.applyIf(options, { 
	  node: node 
	}); 
    //动态加载时，判断是加载前是否清理Store node
    options.params[me.nodeParam] = node ? node.getId() : 'root'; 


	if (node) { 
	  node.set('loading', true); 
	} 


    return me.callParent([options]); 
    } 
});