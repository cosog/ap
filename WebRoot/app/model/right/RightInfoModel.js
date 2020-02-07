Ext.define('AP.model.right.RightInfoModel', {
    extend: 'Ext.data.Model',
    fields: ['rightNo', 'rightId', 'rightPwd', 'rightName',
     'rightInEmail', 'rightPhone', 'rightTitle', 'rightRegtime'],
    idProperty: 'rightNo'
});