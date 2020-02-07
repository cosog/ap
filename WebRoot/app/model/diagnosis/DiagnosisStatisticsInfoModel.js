Ext.define('AP.model.diagnosis.DiagnosisStatisticsInfoModel', {
    extend: 'Ext.data.Model',
    fields: ['id1', 'gkmc1', 'gklx1', 'total1', 'id2', 'gkmc2',
     'gklx2', 'total2', 'id3', 'gkmc3', 'gklx3', 'total3'],
    idProperty: 'threadid'
});