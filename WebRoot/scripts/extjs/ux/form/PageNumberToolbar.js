Ext.define('Ext.ux.PageNumberToolbar', {
    extend: 'Ext.toolbar.Toolbar',
    alias: 'widget.pageNumberToolbar',
    alternateClassName: 'Ext.PageNumberToolbar',
    requires: ['Ext.toolbar.TextItem', 'Ext.form.field.Number'],
    /**
     * @cfg {Ext.data.Store} store (required)
     * The {@link Ext.data.Store} the paging toolbar should use as its data source.
     */

    /**
     * @cfg {Boolean} displayInfo
     * true to display the displayMsg
     */
    displayInfo: false,

    /**
     * @cfg {Boolean} prependButtons
     * true to insert any configured items _before_ the paging buttons.
     */
    prependButtons: false,

    /**
     * @cfg {String} displayMsg
     * The paging status message to display. Note that this string is
     * formatted using the braced numbers {0}-{2} as tokens that are replaced by the values for start, end and total
     * respectively. These tokens should be preserved when overriding this string if showing those values is desired.
     */
    displayMsg : '当前记录 {0} -- {1} 条 共 {2} 条记录', 

    /**
     * @cfg {String} emptyMsg
     * The message to display when no records are found.
     */
    emptyMsg : '没有记录可显示',

    /**
     * @cfg {String} beforePageText
     * The text displayed before the input item.
     */
    beforePageText : '当前页',

    /**
     * @cfg {String} afterPageText
     * Customizable piece of the default paging text. Note that this string is formatted using
     * {0} as a token that is replaced by the number of total pages. This token should be preserved when overriding this
     * string if showing the total page count is desired.
     */
    afterPageText : '共{0}页',

    /**
     * @cfg {String} firstText
     * The quicktip text displayed for the first page button.
     * **Note**: quick tips must be initialized for the quicktip to show.
     */
    firstText : '第一页',

    /**
     * @cfg {String} prevText
     * The quicktip text displayed for the previous page button.
     * **Note**: quick tips must be initialized for the quicktip to show.
     */
    prevText : '上一页',

    /**
     * @cfg {String} nextText
     * The quicktip text displayed for the next page button.
     * **Note**: quick tips must be initialized for the quicktip to show.
     */
    nextText : '下一页',

    /**
     * @cfg {String} lastText
     * The quicktip text displayed for the last page button.
     * **Note**: quick tips must be initialized for the quicktip to show.
     */
    lastText : '最后页',

    /**
     * @cfg {String} refreshText
     * The quicktip text displayed for the Refresh button.
     * **Note**: quick tips must be initialized for the quicktip to show.
     */
    refreshText : '刷新',

    /**
     * @cfg {Number} inputItemWidth
     * The width in pixels of the input field used to display and change the current page number.
     */
    inputItemWidth : 30,

    /**
     * Gets the standard paging items in the toolbar
     * @private
     */
    getPagingItems: function() {
        var me = this;

        return [{
            itemId: 'first',
            tooltip: me.firstText,
            overflowText: me.firstText,
            iconCls: Ext.baseCSSPrefix + 'tbar-page-first',
            disabled: true,
            handler: me.moveFirst,
            scope: me
        },{
            itemId: 'prev',
            tooltip: me.prevText,
            overflowText: me.prevText,
            iconCls: Ext.baseCSSPrefix + 'tbar-page-prev',
            disabled: true,
            handler: me.movePrevious,
            scope: me
        },
        '-',
        me.beforePageText,
        {
            xtype: 'numberfield',
            itemId: 'inputItem',
            name: 'inputItem',
            cls: Ext.baseCSSPrefix + 'tbar-page-number',
            allowDecimals: false,
            minValue: 1,
            hideTrigger: true,
            enableKeyEvents: true,
            selectOnFocus: true,
            submitValue: false,
            width: me.inputItemWidth,
            margins: '-1 2 3 2',
            listeners: {
                scope: me,
                keydown: me.onPagingKeyDown,
                blur: me.onPagingBlur
            }
        },{
            xtype: 'tbtext',
            itemId: 'afterTextItem',
            text: Ext.String.format(me.afterPageText, 1)
        },{
            xtype: 'numberfield',  
            hideTrigger: true,
            name: 'pageSizeItem',
            fieldLabel : '<font color=#666666>,每页显示</font>', 
            itemId: 'pageSizeItem', 
			value:defaultPageSize,
			width:120,
			labelWidth: 70 ,  
			allowDecimals: false,
            minValue: 1,
            hideTrigger: true,
            enableKeyEvents: true,
            selectOnFocus: true,
            submitValue: false, 
            listeners: {
                scope: me,
                specialkey: me.onPageSizeKeyDown ,
                select:me.onPageSizeSelect
            }
            
        } ,
        
        {xtype : "label",html:"&nbsp<font color=#666666>条 </font> "},
        '-',
        {
            itemId: 'next',
            tooltip: me.nextText,
            overflowText: me.nextText,
            iconCls: Ext.baseCSSPrefix + 'tbar-page-next',
            disabled: true,
            handler: me.moveNext,
            scope: me
        },{
            itemId: 'last',
            tooltip: me.lastText,
            overflowText: me.lastText,
            iconCls: Ext.baseCSSPrefix + 'tbar-page-last',
            disabled: true,
            handler: me.moveLast,
            scope: me
        },
        '-',
        {
            itemId: 'refresh',
            tooltip: me.refreshText,
            overflowText: me.refreshText,
            iconCls: Ext.baseCSSPrefix + 'tbar-loading',
            handler: me.doRefresh,
            scope: me
        }];
    },

    initComponent : function(){
        var me = this,
            pagingItems = me.getPagingItems(),
            userItems   = me.items || me.buttons || [];

        if (me.prependButtons) {
            me.items = userItems.concat(pagingItems);
        } else {
            me.items = pagingItems.concat(userItems);
        }
        delete me.buttons;

        if (me.displayInfo) {
            me.items.push('->'); 
            me.items.push({xtype: 'tbtext', itemId: 'displayItem'});
          
        }

        me.callParent();
        
//        me.fireEvent('change');
//        me.fireEvent('beforechange');

//        me.addEvents(
//            /**
//             * @event change
//             * Fires after the active page has been changed.
//             * @param {Ext.toolbar.Paging} this
//             * @param {Object} pageData An object that has these properties:
//             *
//             * - `total` : Number
//             *
//             *   The total number of records in the dataset as returned by the server
//             *
//             * - `currentPage` : Number
//             *
//             *   The current page number
//             *
//             * - `pageCount` : Number
//             *
//             *   The total number of pages (calculated from the total number of records in the dataset as returned by the
//             *   server and the current {@link Ext.data.Store#pageSize pageSize})
//             *
//             * - `toRecord` : Number
//             *
//             *   The starting record index for the current page
//             *
//             * - `fromRecord` : Number
//             *
//             *   The ending record index for the current page
//             */
//            'change',
//
//            /**
//             * @event beforechange
//             * Fires just before the active page is changed. Return false to prevent the active page from being changed.
//             * @param {Ext.toolbar.Paging} this
//             * @param {Number} page The page number that will be loaded on change
//             */
//            'beforechange'
//        );
        me.on('afterlayout', me.onLoad, me, {single: true});

        me.bindStore(me.store || 'ext-empty-store', true);
    },
    // private
    updateInfo : function(){
        var me = this,
            displayItem = me.child('#displayItem'),
            store = me.store,
            pageData = me.getPageData(),
            count, msg;

        if (displayItem) {
            count = store.getCount();
            if (count === 0) {
                msg = me.emptyMsg;
            } else {
                msg = Ext.String.format(
                    me.displayMsg,
                    pageData.fromRecord,
                    pageData.toRecord,
                    pageData.total
                );
            }
            displayItem.setText(msg);
//            me.doComponentLayout();
        } 
    },

    // private
    onLoad : function(){
        var me = this,
            pageData,
            currPage,
            pageCount,
            afterText;

        if (!me.rendered) {
            return;
        }

        pageData = me.getPageData();
        currPage = pageData.currentPage;
        pageCount = pageData.pageCount;
        afterText = Ext.String.format(me.afterPageText, isNaN(pageCount) ? 1 : pageCount);

        me.child('#afterTextItem').setText(afterText);
        me.child('#pageSizeItem');
        me.child('#inputItem').setValue(currPage);
        me.child('#first').setDisabled(currPage === 1);
        me.child('#prev').setDisabled(currPage === 1);
        me.child('#next').setDisabled(currPage === pageCount);
        me.child('#last').setDisabled(currPage === pageCount);
        me.child('#refresh').enable();
        me.updateInfo();
        me.fireEvent('change', me, pageData);
    },

    // private
    getPageData : function(){
        var store = this.store,
            totalCount = store.getTotalCount();

        return {
            total : totalCount,
            currentPage : store.currentPage,
            pageCount: Math.ceil(totalCount / store.pageSize),
            fromRecord: ((store.currentPage - 1) * store.pageSize) + 1,
            toRecord: Math.min(store.currentPage * store.pageSize, totalCount)

        };
    },

    // private
    onLoadError : function(){
        if (!this.rendered) {
            return;
        }
        this.child('#refresh').enable();
    },

    // private
    readPageFromInput : function(pageData){
        var v = this.child('#inputItem').getValue(),
            pageNum = parseInt(v, 10);

        if (!v || isNaN(pageNum)) {
            this.child('#inputItem').setValue(pageData.currentPage);
            return false;
        }
        return pageNum;
    },

    onPagingFocus : function(){
        this.child('#inputItem').select();
    },

    //private
    onPagingBlur : function(e){
        var curPage = this.getPageData().currentPage;
        this.child('#inputItem').setValue(curPage);
    },
    onPageSizeSelect:function(filed,obj){
    	 this.store.pageSize=filed.value;  
		   if(this.fireEvent('beforechange', this, 1) !== false){ 
			   this.store.loadPage(1);
	        }
    },
    onPageSizeKeyDown:function(filed, e){ 
    	 if (e.getKey() == e.ENTER) {
    	 var ore=this.store;
    	 var rv_=1;
    	 var opsize=filed.rawValue;
    	 if(isNotVal(opsize)&&opsize!="0"&&opsize!=0){
    	   rv_=filed.rawValue;
    	 }
    	 ore.pageSize=rv_;    
         var totalCount = ore.getTotalCount();
         var pageCount= Math.ceil(totalCount / ore.pageSize);
    	 var currPage = this.getPageData().currentPage; 
    	 this.child('#inputItem').setValue(currPage);
    	  this.child('#pageSizeItem').setValue(this.getPageData().toRecord);
    	 
    	 var afterText = Ext.String.format(this.afterPageText, isNaN(pageCount) ? 1 : pageCount); 
    	 this.child('#afterTextItem').setText(afterText);
    	 if(this.fireEvent('beforechange', this, 1) !== false){ 
			   this.store.loadPage(1);
	        }  
    	 }
    },
    // private
    onPagingKeyDown : function(field, e){
        var me = this,
            k = e.getKey(),
            pageData = me.getPageData(),
            increment = e.shiftKey ? 10 : 1,
            pageNum;

        if (k == e.RETURN) {
            e.stopEvent();
            pageNum = me.readPageFromInput(pageData);
            if (pageNum !== false) {
                pageNum = Math.min(Math.max(1, pageNum), pageData.pageCount);
                if(me.fireEvent('beforechange', me, pageNum) !== false){
                    me.store.loadPage(pageNum);
                }
            }
        } else if (k == e.HOME || k == e.END) {
            e.stopEvent();
            pageNum = k == e.HOME ? 1 : pageData.pageCount;
            field.setValue(pageNum);
        } else if (k == e.UP || k == e.PAGEUP || k == e.DOWN || k == e.PAGEDOWN) {
            e.stopEvent();
            pageNum = me.readPageFromInput(pageData);
            if (pageNum) {
                if (k == e.DOWN || k == e.PAGEDOWN) {
                    increment *= -1;
                }
                pageNum += increment;
                if (pageNum >= 1 && pageNum <= pageData.pages) {
                    field.setValue(pageNum);
                }
            }
        }
    },

    // private
    beforeLoad : function(){
        if(this.rendered && this.refresh){
            this.refresh.disable();
        }  
    },

    // private
    doLoad : function(start){
        if(this.fireEvent('beforechange', this, o) !== false){
            this.store.load();
        }
    },

    /**
     * Move to the first page, has the same effect as clicking the 'first' button.
     */
    moveFirst : function(){
        if (this.fireEvent('beforechange', this, 1) !== false){
            this.store.loadPage(1);
        }
    },

    /**
     * Move to the previous page, has the same effect as clicking the 'previous' button.
     */
    movePrevious : function(){
        var me = this,
            prev = me.store.currentPage - 1;

        if (prev > 0) {
            if (me.fireEvent('beforechange', me, prev) !== false) {
                me.store.previousPage();
            }
        }
    },

    /**
     * Move to the next page, has the same effect as clicking the 'next' button.
     */
    moveNext : function(){
    	 var v_ts=this.child('#pageSizeItem'); 
    	 var me = this; 
         if(!Ext.isEmpty(v_ts)){
         	me.store.pageSize=v_ts.value;
         }else{
         	me.store.pageSize=25;
         }  
        var    total = me.getPageData().pageCount;
        var    next = me.store.currentPage + 1;
        
        if (next <= total) {
            if (me.fireEvent('beforechange', me, next) !== false) {
                me.store.nextPage();
            }
        } 
    },

    /**
     * Move to the last page, has the same effect as clicking the 'last' button.
     */
    moveLast : function(){
        var me = this,
            last = me.getPageData().pageCount;

        if (me.fireEvent('beforechange', me, last) !== false) {
            me.store.loadPage(last);
        }
    },

    /**
     * Refresh the current page, has the same effect as clicking the 'refresh' button.
     */
    doRefresh : function(){
        var me = this,
            current = me.store.currentPage;

        if (me.fireEvent('beforechange', me, current) !== false) {
            me.store.loadPage(current);
        }
    }, 
    /**
     * Binds the paging toolbar to the specified {@link Ext.data.Store}
     * @param {Ext.data.Store} store The store to bind to this toolbar
     * @param {Boolean} initial (Optional) true to not remove listeners
     */
    bindStore : function(store, initial){
        var me = this;
        
         
        if (!initial && me.store) {
            if(store !== me.store && me.store.autoDestroy){
                me.store.destroyStore();
            }else{
                me.store.un('beforeload', me.beforeLoad, me);
                me.store.un('load', me.onLoad, me);
                me.store.un('exception', me.onLoadError, me);
            }
            if(!store){
                me.store = null;
            }
        }
        if (store) {
            store = Ext.data.StoreManager.lookup(store);
            store.on({
                scope: me,
                beforeload: me.beforeLoad,
                load: me.onLoad,
                exception: me.onLoadError
            });
        }
        me.store = store;
    },

    /**
     * Unbinds the paging toolbar from the specified {@link Ext.data.Store} **(deprecated)**
     * @param {Ext.data.Store} store The data store to unbind
     */
    unbind : function(store){
        this.bindStore(null);
    },

    /**
     * Binds the paging toolbar to the specified {@link Ext.data.Store} **(deprecated)**
     * @param {Ext.data.Store} store The data store to bind
     */
    bind : function(store){
        this.bindStore(store);
    },

    // private
    onDestroy : function(){
        this.bindStore(null);
        this.callParent();
    }
});