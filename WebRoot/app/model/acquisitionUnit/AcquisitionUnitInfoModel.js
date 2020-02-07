Ext.define('AP.model.acquisitionUnit.AcquisitionUnitInfoModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'id',
        type: 'int'
     }, {
        name: 'unitCode',
        type: 'string'
     }, {
        name: 'unitName',
        type: 'string'
     }, {
        name: 'remark',
        type: 'string'
     }],
    idProperty: 'threadid'

});