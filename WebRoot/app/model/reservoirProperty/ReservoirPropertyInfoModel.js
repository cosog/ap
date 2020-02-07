Ext.define('AP.model.reservoirProperty.ReservoirPropertyInfoModel', {
    extend: 'Ext.data.Model',
    fields: [{
            name: 'jlbh',
            type: 'int',
            defaultValue:''
 }, {
            name: 'yqcbh',
            type: 'string',
            defaultValue:''
 }, {
            name: 'resName',
            type: 'string',
            defaultValue:''
 }, {
            name: 'yymd',
            type: 'number',
            defaultValue:''
 }, {
            name: 'smd',
            type: 'number',
            defaultValue:''
 }, {
            name: 'trqxdmd',
            type: 'number',
            defaultValue:''
 },{
            name: 'bhyl',
            type: 'number',
            defaultValue:''
 },{
            name: 'yqcyl',
            type: 'number',
            defaultValue:''
 }, {
            name: 'yqczbsd',
            type: 'number',
            defaultValue:''
 }, {
            name: 'yqczbwd',
            type: 'number',
            defaultValue:''
 }],
    idProperty: 'threadid'

});