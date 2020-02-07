Ext.define('AP.model.reportOut.ReportPumpUnitDayWellModel', {
    extend: 'Ext.data.Model',
    fields: ['id','jhh', 'jh', 'rgzsj', 'jssj', 'jsdjrcyl', 'jsdjrcyl1', 'pfdjrcyl', 'pfdjrcyl1', 'jsdjrcylf', 'jsdjrcyl1f', 'pfdjrcylf', 'pfdjrcyl1f', 'hsl', 'hsld', 'bz',{
      	 name: 'yyts',
       	 type: 'int'
        },'gtcmxs'],
    idProperty: 'id'
})