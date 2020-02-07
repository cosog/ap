Ext.define("AP.model.well.WellsiteInfoModel", {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'jlbh',
        type: 'int',
        defaultValue:0
     }, {
        name: 'dwbh',
        type: 'string',
        defaultValue:''
     }, {
        name: 'yqcbh',
        type: 'string',
        defaultValue:''
     }, {
        name: 'orgName',
        type: 'string',
        defaultValue:''
     }, {
        name: 'resName',
        type: 'string',
        defaultValue:''
     }, {
        name: 'jc',
        type: 'string',
        defaultValue:''
     },{
        name: 'jclx',
        type: 'int',
        defaultValue:0
     }, {
        name: 'jclxName',
        type: 'string',
        defaultValue:''
     },{
        name: 'lng',
        type: 'float',
        defaultValue:-9999
     },{
        name: 'lat',
        type: 'float',
        defaultValue:-9999
     },{
        name: 'showLevel',
        type: 'int',
        defaultValue:0
     },{
        name: 'pxbh',
        type: 'int',
        defaultValue:0
     }],
    idProperty: 'threadid'
})