Ext.define("AP.model.well.WellInfoModel", {
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
     }, {
        name: 'jhh',
        type: 'string',
        defaultValue:''
     }, {
        name: 'jh',
        type: 'string',
        defaultValue:''
     }, {
        name: 'jlx',
        type: 'int',
        defaultValue:0
     }, {
         name: 'jslx',
         type: 'int',
         defaultValue:0
      },{
        name: 'jlxName',
        type: 'string',
        defaultValue:''
     }, {
         name: 'jslxName',
         type: 'string',
         defaultValue:''
      }, {
        name: 'ssjwName',
        type: 'string',
        defaultValue:''
     }, {
        name: 'sszcdyName',
        type: 'string',
        defaultValue:''
     }, {
        name: 'ssjw',
        type: 'int',
        defaultValue:0
     }, {
        name: 'sszcdy',
        type: 'int',
        defaultValue:0
     }, {
        name: 'rgzsjd',
        type: 'string',
        defaultValue:''
     }, {
        name: 'rgzsj',
        type: 'float',
        defaultValue:0
     },{
         name: 'mpcch',
         type: 'float',
         defaultValue:0
      },{
          name: 'qbc',
          type: 'float',
          defaultValue:0
       }, {
         name: 'dmx',
         type: 'float',
         defaultValue:-9999
      }, {
          name: 'dmy',
          type: 'float',
          defaultValue:-9999
      }, {
           name: 'showLevel',
           type: 'int',
           defaultValue:2
      }, {
          name: 'pxbh',
          type: 'int',
          defaultValue:9999
     }],
    idProperty: 'threadid'
})