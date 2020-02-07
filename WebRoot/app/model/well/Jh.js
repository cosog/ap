Ext.define("AP.model.well.Jh", {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'jhId',
        type: 'int'
     }, {
        name: 'jh',
        type: 'string'
     }],
    idProperty: 'threadid'
})