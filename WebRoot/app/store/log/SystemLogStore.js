Ext.define('AP.store.log.SystemLogStore', {
    extend: 'Ext.data.Store',
    alias: 'widget.systemLogStore',
    fields: ['id','createTime','user_id','loginIp','action','actionName','remark'],
    autoLoad: true,
    pageSize: defaultPageSize,
    proxy: {
        type: 'ajax',
        url: context + '/logQueryController/getSystemLogData',
        actionMethods: {
            read: 'POST'
        },
    reader: {
            type: 'json',
            rootProperty: 'totalRoot',
            totalProperty: 'totalCount',
            keepRawData: true
        }
    },
    listeners: {
        load: function (store, record, f, op, o) {
            //获得列表数
            var get_rawData = store.proxy.reader.rawData;
            var arrColumns = get_rawData.columns;
            var column = createSystemLogColumn(arrColumns);
            Ext.getCmp("SystemLogColumnStr_Id").setValue(column);
            var gridPanel = Ext.getCmp("SystemLogGridPanel_Id");
            if (!isNotVal(gridPanel)) {
                var newColumns = Ext.JSON.decode(column);
                var bbar = new Ext.PagingToolbar({
                	store: store,
                	displayInfo: true
    	        });
                gridPanel = Ext.create('Ext.grid.Panel', {
                    id: "SystemLogGridPanel_Id",
                    border: false,
                    autoLoad: false,
                    bbar: bbar,
                    columnLines: true,
                    forceFit: false,
                    viewConfig: {
                    	emptyText: "<div class='con_div_' id='div_dataactiveid'><" + loginUserLanguageResource.emptyMsg + "></div>"
                    },
                    store: store,
                    columns: newColumns,
                    listeners: {
                    	selectionchange: function (view, selected, o) {
                    		
                    	},
                    	select: function(grid, record, index, eOpts) {}
                    }
                });
                var panel = Ext.getCmp("SystemLogPanel_Id");
                panel.add(gridPanel);
            }
            
            var startDate=Ext.getCmp('SystemLogQueryStartDate_Id');
            if(startDate.rawValue==''||null==startDate.rawValue){
            	startDate.setValue(get_rawData.start_date.split(' ')[0]);
            	Ext.getCmp('SystemLogQueryStartTime_Hour_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('SystemLogQueryStartTime_Minute_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[1]);
//            	Ext.getCmp('SystemLogQueryStartTime_Second_Id').setValue(get_rawData.start_date.split(' ')[1].split(':')[2]);
            }
            var endDate=Ext.getCmp('SystemLogQueryEndDate_Id');
            if(endDate.rawValue==''||null==endDate.rawValue){
            	endDate.setValue(get_rawData.end_date.split(' ')[0]);
            	Ext.getCmp('SystemLogQueryEndTime_Hour_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[0]);
            	Ext.getCmp('SystemLogQueryEndTime_Minute_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[1]);
//            	Ext.getCmp('SystemLogQueryEndTime_Second_Id').setValue(get_rawData.end_date.split(' ')[1].split(':')[2]);
            }
        },
        beforeload: function (store, options) {
        	var orgId = Ext.getCmp('leftOrg_Id').getValue();
        	var orgSelection= Ext.getCmp("IframeView_Id").getSelectionModel().getSelection();
        	var iframeViewStore=Ext.getCmp("IframeView_Id").getStore();
        	if(orgSelection.length==0 && iframeViewStore.getCount()>0&&iframeViewStore.getAt(0).data.text==loginUserLanguageResource.orgRootNode){
        		orgId='';
        	}else if(orgSelection.length>0 && orgSelection[0].data.text==loginUserLanguageResource.orgRootNode){
        		orgId='';
        	}
        	var startDate=Ext.getCmp('SystemLogQueryStartDate_Id').rawValue;
        	var startTime_Hour=Ext.getCmp('SystemLogQueryStartTime_Hour_Id').getValue();
        	var startTime_Minute=Ext.getCmp('SystemLogQueryStartTime_Minute_Id').getValue();
//        	var startTime_Second=Ext.getCmp('SystemLogQueryStartTime_Second_Id').getValue();
        	var startTime_Second=0;
            var endDate=Ext.getCmp('SystemLogQueryEndDate_Id').rawValue;
            var endTime_Hour=Ext.getCmp('SystemLogQueryEndTime_Hour_Id').getValue();
        	var endTime_Minute=Ext.getCmp('SystemLogQueryEndTime_Minute_Id').getValue();
//        	var endTime_Second=Ext.getCmp('SystemLogQueryEndTime_Second_Id').getValue();
        	var endTime_Second=0;
        	
        	var selectUserId=Ext.getCmp('systemLogUserListComb_Id').getValue();
        	var operationType=Ext.getCmp('systemLogActionListComb_Id').getValue();
            var new_params = {
                    orgId: orgId,
                    selectUserId:selectUserId,
                    operationType:operationType,
                    startDate:getDateAndTime(startDate,startTime_Hour,startTime_Minute,startTime_Second),
                    endDate:getDateAndTime(endDate,endTime_Hour,endTime_Minute,endTime_Second)
                };
            Ext.apply(store.proxy.extraParams, new_params);
        },
        datachanged: function (v, o) {
            //onLabelSizeChange(v, o, "statictisTotalsId");
        }
    }
});