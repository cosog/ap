Ext.define('AP.model.productionData.ProductionOutInfoModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'jlbh',
        type: 'int',
        defaultValue:0
      }, {
        name: 'jh',
        type: 'string',
        defaultValue:''
      }, {
        name: 'jslx',
        type: 'int',
        defaultValue:201
      }, {
        name: 'jslxName',
        type: 'string',
        defaultValue:''
      }, {
        name: 'qtlx',
        type: 'int',
        defaultValue:1
      }, {
        name: 'qtlxName',
        type: 'string',
        defaultValue:''
      }, {
        name: 'sfpfcl',
        type: 'int',
        defaultValue:1
      }, {
        name: 'sfpfclName',
        type: 'string',
        defaultValue:'不劈分'
      }, {
        name: 'ccjzt',
        type: 'int',
        defaultValue:1
      }, {
        name: 'ccjztName',
        type: 'string',
        defaultValue:''
       }, {
        name: 'hsl',
        type: 'float',
        defaultValue:0,
      }, {
        name: 'yy',
        type: 'float',
        defaultValue:0
       }, {
        name: 'ty',
        type: 'float',
        defaultValue:0
      }, {
        name: 'hy',
        type: 'float',
        defaultValue:0
      }, {
        name: 'dym',
        type: 'float',
        defaultValue:0
      }, {
        name: 'bg',
        type: 'float',
        defaultValue:0
      }, {
        name: 'jklw',
        type: 'float',
        defaultValue:0
      }, {
        name: 'scqyb',
        type: 'float',
        defaultValue:0
      }, {
        name: 'rcql',
        type: 'float',
        defaultValue:0
      }, {
        name: 'bj',
        type: 'float',
        defaultValue:0
      },{
    	 name:'blxName',
    	 type:'string',
         defaultValue:''
      },{
        name: 'bjb',
        type: 'int',
        defaultValue:1
      }, {
        name: 'bjbName',
        type: 'string',
        defaultValue:''
      },{
    	name:'btlxName',
    	type:'string',
        defaultValue:''
      }, {
        name: 'zsc',
        type: 'float',
        defaultValue:0
      }, {
        name: 'ygnj',
        type: 'float',
        defaultValue:0
      }, {
        name: 'yctgnj',
        type: 'float',
        defaultValue:0
      }, {
        name: 'yjgj',
        type: 'float',
        defaultValue:0
      }, {
        name: 'yjgjb',
        type: 'string',
        defaultValue:''
      }, {
        name: 'yjgcd',
        type: 'float',
        defaultValue:0
      }, {
        name: 'ejgj',
        type: 'float',
        defaultValue:0
      }, {
        name: 'ejgjb',
        type: 'string',
        defaultValue:''
      }, {
        name: 'ejgcd',
        type: 'float',
        defaultValue:0
      }, {
        name: 'sjgj',
        type: 'float',
        defaultValue:0
      }, {
        name: 'sjgjb',
        type: 'string',
        defaultValue:''
      }, {
        name: 'sjgcd',
        type: 'float',
        defaultValue:0
      }, {
        name: 'sijgj',
        type: 'float',
        defaultValue:0
      }, {
        name: 'sijgjb',
        type: 'string',
        defaultValue:''
      }, {
        name: 'sijgcd',
        type: 'float',
        defaultValue:0
      }, {
        name: 'mdzt',
        type: 'int',
        defaultValue:0
      }, {
        name: 'mdztName',
        type: 'string',
        defaultValue:''
      }, {
        name: 'jmb',
        type: 'float',
        defaultValue:1
      }, {
        name: 'bzgtbh',
        type: 'int',
        defaultValue:0
      }, {
        name: 'bzdntbh',
        type: 'int',
        defaultValue:0
      }, {
          name: 'cjsj',
          type: 'string',
          defaultValue:''
        }],
    idProperty: 'threadid'
});