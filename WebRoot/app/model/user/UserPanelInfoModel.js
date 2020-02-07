Ext.define('AP.model.user.UserPanelInfoModel', {
    extend: 'Ext.data.Model',
    fields: ['userNo', 'userId', 'userPwd', 'userType', 'userTypeName', 'userName',
     'orgName', 'userTitleName', 'userInEmail', 'userOutEmail', 'userPhone', 'userMobile',
     'userTitle', 'userOrgid', 'userRegtime','userQuickLogin','userQuickLoginName']
});