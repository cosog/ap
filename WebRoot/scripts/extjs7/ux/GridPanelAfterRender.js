Ext.override(Ext.grid.GridPanel, {
    afterRender : Ext.Function.createSequence(Ext.grid.GridPanel.prototype.afterRender,
        function() {
//            if (!this.cellTip) {
//                return;
//            }
            var view = this.getView();
            this.tip = new Ext.ToolTip({
                target : view.el,
                delegate : '.x-grid-cell-inner',
                trackMouse : true,
                renderTo : document.body,
                listeners : {
                    beforeshow : function updateTipBody(tip) {
                        if (Ext.isEmpty(tip.triggerElement.innerText)) {
                            return false;
                        }
                        tip.body.dom.innerHTML = tip.triggerElement.innerText;
                    }
                }
            });
        })
});