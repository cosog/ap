Ext.define('AP.model.right.RightUserInfoModel', {
    extend: 'Ext.data.Model',
    fields: ['userNo', 'userId', 'userPwd', 'userName', 'userInEmail',
     'userPhone', 'userTitle', 'userOrgid', 'userRegtime'],
    idProperty: 'userNo'
});