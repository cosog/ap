Ext.define("AP.model.well.WellInModel", {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'jlbh',
        type: 'int'
     }, {
        name: 'jh',
        type: 'string'
     }],
    idProperty: 'threadid'
})