Ext.define('AP.model.acquisitionUnit.AcquisitionItemsTreeInfoModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'jlbh',
        type: 'int'
   }, {
        name: 'text',
        type: 'string'
   }, {
        name: 'itemName',
        type: 'string'
   }, {
        name: 'itemCode',
        type: 'string'
   }, {
        name: 'address',
        type: 'string'
   }, {
        name: 'length',
        type: 'string'
   }, {
        name: 'dataType',
        type: 'string'
   }, {
        name: 'zoom',
        type: 'string'
   },{
        name: 'parentid',
        type: 'string'
   }],
    idProperty: 'mdId'
});