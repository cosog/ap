var helper={};
Ext.define('AP.view.well.SMSDeviceInfoView', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.SMSDeviceInfoView',
    id: 'SMSDeviceInfoView_Id',
    layout: 'fit',
    border: false,
//    autoScroll: true,
//    scrollable: true,
    initComponent: function () {
        Ext.apply(this, {
        	layout: 'border',
        	items: [{
        		region: 'center',
        		html: '<div style="width:100%;height:100%;"><div style="width:100%;height:100%;" id="SMSDeviceTableDivTest_id"></div></div>',
        		listeners: {
                    resize: function (abstractcomponent, adjWidth, adjHeight, options) {
                    	if(helper.SMSDeviceTableHot!=null){
//                    		const example = document.querySelector('#SMSDeviceTableDivTest_id');
//                        	const sliceElem = example.parentElement;
//                        	sliceElem.style.width = adjWidth;
//                        	sliceElem.style.height = adjHeight;
                        	helper.SMSDeviceTableHot.refreshDimensions();
                    	}
                    }
                }
        	},{
        		region: 'east',
        		width: '30%',
            	split: true,
            	collapsible: true
        	}]
        })
        this.callParent(arguments);
    }
});

function CreateAndLoadSMSDeviceInfoTable() {
//	if (SMSDeviceTableHot !=null && SMSDeviceTableHot != undefined) {
//		SMSDeviceTableHot.destroy();
//	}
//	$('#SMSDeviceTableDivTest_id').empty();
	const hotElement = document.querySelector('#SMSDeviceTableDivTest_id');
	helper.SMSDeviceTableHot =new Handsontable(hotElement, {
		licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
		data: [{
            "id": "62",
            "orgName": "组织根节点",
            "wellName": "sms1",
            "instanceName": "短信实例LQ1000",
            "signInId": "12345678901",
            "sortNum": "1001"
        }, {
            "id": "102",
            "orgName": "采油二队",
            "wellName": "smsTest",
            "instanceName": "短信实例LQ1000",
            "signInId": "12345678902",
            "sortNum": "1002"
        }, {
            "id": "123",
            "orgName": "采油一队",
            "wellName": "sms2",
            "instanceName": "短信实例LQ1000",
            "signInId": "854275868",
            "sortNum": "1003"
        }],
        colHeaders: ['序号', '设备名称', '短信设备实例', '注册包ID', '排序编号'], //显示列头
        columns: [{
            data: 'id'
        }, {
            data: 'wellName'
        }, {
            data: 'instanceName',
            type: 'dropdown',
            strict: true,
            allowInvalid: false,
            source: ['短信实例LQ1000', 'test']
        }, {
            data: 'signInId'
        }, {
            data: 'sortNum',
            type: 'text'
        }],
        colWidths: 100,
        stretchH: 'all',
        hiddenColumns: {
            columns: [0],
            indicators: false
        },
        contextMenu: true,
        multiColumnSorting: true,
        filters: true,
        rowHeaders: true,
        manualRowMove: true
    });
};