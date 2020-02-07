Ext.define("AP.model.well.SinglewellModel", {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'jlbh',
        type: 'int'
     }, {
        name: 'jbh',
        type: 'int'
     }, {
        name: 'jc',
        type: 'string'
     }, {
        name: 'jhh',
        type: 'string'
     }, {
        name: 'jh',
        type: 'string'
     }, {
        name: 'rcyl',
        type: 'number'
     }, {
        name: 'cjsj',
        type: 'string'
     }],
    idProperty: 'threadid'
})