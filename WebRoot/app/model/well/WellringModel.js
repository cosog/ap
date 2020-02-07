Ext.define("AP.model.well.WellringModel", {
    extend: 'Ext.data.Model',
    fields: [{
            name: 'jlbh',
            type: 'int',
            defaultValue:0
     }, {
            name: 'jhh',
            type: 'string',
            defaultValue:''
     }, {
            name: 'jljhcsrcyl',
            type: 'number',
            defaultValue:0
     }, {
            name: 'jhcsl',
            type: 'number',
            defaultValue:0
     }, {
            name: 'jljhrcyl',
            type: 'number',
            defaultValue:0
     }, {
            name: 'jljhrcyld',
            type: 'number',
            defaultValue:0
     }, {
            name: 'gxrq',
            type: 'string',
            defaultValue:''
     }],
    idProperty: 'threadid'
});