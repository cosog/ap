Ext.define('AP.store.diagnosis.WorkStatusStatisticsInfoStore', {
	extend : 'Ext.data.Store',
	autoLoad : true,
	pageSize : defaultPageSize,
	proxy : {
		type : 'ajax',
		url : context
				+ '/diagnosisAnalysisOnlyController/statisticsData',
		actionMethods : {
			read : 'POST'
		},
		params : {
			start : 0,
			limit : defaultPageSize
		},
		reader : {
			type : 'json',
			rootProperty : 'list',
			totalProperty : 'totals',
            keepRawData: true
		}
	},
	listeners : {
		load:function(store,record,f,op,o){
			initFSDiagramAnalysisSingleStatPieOrColChat(store);
			
			var gridPanel = Ext.getCmp("FSDiagramAnalysisSingleDetails_Id");
            if (isNotVal(gridPanel)) {
            	gridPanel.getStore().load();
            }else{
            	Ext.create('AP.store.diagnosis.SingleAnalysisiListStore');
            }
		},
		beforeload : function(store, options) {
			var type=getFSDiagramAnalysisSingleStatType().type;
			var orgId = Ext.getCmp('leftOrg_Id').getValue();
			var new_params = {
				orgId:orgId,
				type:type,
				wellType:200
			};
			Ext.apply(store.proxy.extraParams, new_params);
		}
	}
});

createWellStatusStiColumn = function(columnInfo) {
	var myArr = columnInfo;
	var myColumns = "[";
	for (var i = 0; i < myArr.length; i++) {
		var attr = myArr[i];
		var width_="";
		var lock_="";
		var hidden_="";
		if(isNotVal(attr.lock)){
//			lock_=",locked:"+attr.lock;
		}
		if(isNotVal(attr.hidden==true)){
			hidden_=",hidden:"+attr.hidden;
		}
		if(isNotVal(attr.width)){
			width_=",width:"+attr.width;
		}
		myColumns +="{text:'" + attr.header + "'";
		 if (attr.dataIndex=='id'){
		  myColumns +=",width:"+attr.width+",xtype: 'rownumberer',sortable:false,align:'center',locked:false" ;
		}else if(attr.dataIndex=='gtcjsj'||"updatetime"==attr.dataIndex){
			myColumns +=hidden_+lock_+width_+",sortable : false,align:'center',dataIndex:'"+attr.dataIndex+"',renderer:Ext.util.Format.dateRenderer('Y-m-d H:i:s')";
		}else if(attr.dataIndex=='gxrq'){
			myColumns +=hidden_+lock_+width_+",sortable : false,align:'center',dataIndex:'"+attr.dataIndex+"',renderer:Ext.util.Format.dateRenderer('Y-m-d')";
		}else if(attr.dataIndex=='total'){
			myColumns +=hidden_+lock_+width_+",sortable : false,align:'center',dataIndex:'"+attr.dataIndex+"',renderer:function(value, p, record){return renderWellStatusStiList(value, p, record);}";
		}else {
			myColumns +=hidden_+lock_+width_+",align:'center',dataIndex:'"+attr.dataIndex+"'";
		}
		myColumns += "}";
		if (i < myArr.length - 1) {
			myColumns += ",";
		}
	}
	myColumns +="]";
	return myColumns;
}

renderWellStatusStiList=function (value, p, record) {
	return Ext.String.format('<b><a href=\"javascript:showWellStatusTable(\'{1}\');\"    >{0}</a></b>',value, record.data.gkmc);
}

showWellStatusTable = function(gkmc) {
//	var AbnormalWellGkmc_Id =Ext.getCmp("AbnormalWellGkmc_Id");
//	AbnormalWellGkmc_Id.setValue(gkmc);
//	var jh_tobj = Ext.getCmp('AbnormalWellJhCombo_Id');
//		if (!Ext.isEmpty(jh_tobj)) {
//			jh_tobj.setValue("");
//		}
//	var AbnormalWellGkmc_Id =Ext.getCmp("AbnormalWellListGrid_Id").getStore().load();
}
