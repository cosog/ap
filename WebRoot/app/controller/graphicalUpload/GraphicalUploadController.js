Ext.define('AP.controller.graphicalUpload.GraphicalUploadController', {
    extend: 'Ext.app.Controller',
    refs: [{
    	ref: 'graphicalUploadInfoView',
        selector: 'graphicalUploadInfoView'
    }],
    init: function () {
        this.control({})
    }
});

//功图提交
function submitSurfaceCardFile() {
	var form = Ext.getCmp("SelectSurfaceCardFilePanelFormId");
	var value=Ext.getCmp("SurfaceCardFilefieldId").getValue();
	var surfaceCardType=Ext.getCmp("SurfaceCardTypeListGrid_Id").getSelectionModel().getSelection()[0].data.gtlx;
    if(form.isValid()) {
        form.submit({
            url: context + '/graphicalUploadController/getUploadSurfaceCardFile?surfaceCardType='+surfaceCardType,
            timeout: 1000*60*10,
            method:'post',
            waitMsg: '文件上传中...',
            success: function(fp, o) {
            	Ext.getCmp("SurfaceCardFilefieldId").setRawValue(value);
                Ext.getCmp("SurfaceCardFilefieldId").fileInputEl.set({
                    multiple:'multiple'
                });
                var surfaceCardUploadGridpanel=Ext.getCmp("surfaceCardUploadGridpanelId");
                if(isNotVal(surfaceCardUploadGridpanel)){
                	Ext.getCmp("SelectSurfaceCardFilePanel_Id").remove(surfaceCardUploadGridpanel);
                }
                var result =  Ext.JSON.decode(o.response.responseText);
                var store=Ext.create('Ext.data.Store', {
                    fields:[ 'id', 'wellName', 'acqTime','stroke','spm'],
                    data:result.totalRoot
                });
                var column=createDiagStatisticsColumn(result.columns);
                var newColumns = Ext.JSON.decode(column);
                var gridpanel=Ext.create('Ext.grid.Panel', {
                	id:'surfaceCardUploadGridpanelId',
                	columnLines: true,
                    forceFit: true,
                    stripeRows:true,
                    selType: 'checkboxmodel',
                    multiSelect:true,
                    store: store,
                    columns:newColumns,
                    listeners: {
                    	selectionchange:function(grid, record , eOpts) {
                    		if(record.length>0){
                    			var wellName=record[record.length-1].data.wellName;
                    			var acqTime=record[record.length-1].data.acqTime;
                    			var param=wellName+"@"+acqTime;
                    			Ext.Ajax.request({
                    				url : context + '/graphicalUploadController/getSurfaceCardGraphicalData',
                    				method : "POST",
                    				// 提交参数
                    				params : {
                    					param : param
                    				},
                    				success : function(response) {
                    					var result = Ext.JSON.decode(response.responseText);
                    					if(result!=null){
                    						showSurfaceCardUploadChart(result, "SurfaceCardUploadShowDiv_Id");
                    					}else{
                    						$("#SurfaceCardUploadShowDiv_Id").html('');
                    					}
                    				},
                    				failure : function() {
                    					Ext.Msg.alert("提示", "【<font color=red>图形数据请求失败 </font>】");
                    				}
                    			});
                    		}
                    	}
                    }
                });
                Ext.getCmp("SelectSurfaceCardFilePanel_Id").add(gridpanel);
                if(result.totalCount>0){
                	gridpanel.getSelectionModel().select(0,true);//选中第一行
                }
            },
            failure : function() {
            	Ext.Msg.alert("提示", "【<font color=red>上传失败 </font>】文件内容过大，请减少内容后重新上传！");
			}
        });
    }
    return false;
};

//全部上传
function uploadAllSurfaceCardFile(){
	var surfaceCardUploadGridpanel = Ext.getCmp("surfaceCardUploadGridpanelId");
	if(isNotVal(surfaceCardUploadGridpanel)){
//		var gridPanel_model = surfaceCardUploadGridpanel.getSelectionModel();
//		gridPanel_model.selectAll();
//		var record = gridPanel_model.getSelection();
//		var uploadData = [];
//		Ext.Array.each(record, function(name, index, countriesItSelf) {
//			uploadData.push(record[index].get("wellName")+"@"+record[index].get("acqTime"));
//			});
//		var uploadSurfaceCardList = "" + uploadData.join(",");
		Ext.getCmp("SelectSurfaceCardFilePanel_Id").mask("数据保存中，请稍后...");
		// AJAX提交方式
		Ext.Ajax.request({
			timeout: 1000*60*10,
			url : context + '/graphicalUploadController/saveUploadSurfaceCardFile',
			method : "POST",
			// 提交参数
			params : {
//				uploadSurfaceCardListStr : uploadSurfaceCardList,
				uploadAll:1                                          //是否全部上传
			},
			success : function(response) {
				Ext.getCmp("SelectSurfaceCardFilePanel_Id").unmask("数据保存中，请稍后...");
				var result = Ext.JSON.decode(response.responseText);
				if (result.flag == true) {
					Ext.Msg.alert('提示', "<font color=blue>上传成功</font>");
				}
				if (result.flag == false) {
					Ext.Msg.alert('提示', "<font color=red>上传失败。</font>");
				}
			},
			failure : function() {
				Ext.getCmp("SelectSurfaceCardFilePanel_Id").unmask("数据保存中，请稍后...");
				Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
			}
		});
	}
	return false;
};

//选中上传
function uploadSelectedSurfaceCardFile(){
	var surfaceCardUploadGridpanel = Ext.getCmp("surfaceCardUploadGridpanelId");
	if(isNotVal(surfaceCardUploadGridpanel)){
		var gridPanel_model = surfaceCardUploadGridpanel.getSelectionModel();
		var record = gridPanel_model.getSelection();
		var uploadData = [];
		Ext.Array.each(record, function(name, index, countriesItSelf) {
			uploadData.push(record[index].get("wellName")+"@"+record[index].get("acqTime"));
			});
		var uploadSurfaceCardList = "" + uploadData.join(",");
		Ext.getCmp("SelectSurfaceCardFilePanel_Id").mask("数据保存中，请稍后...");
		// AJAX提交方式
		Ext.Ajax.request({
			timeout: 1000*60*10,
			url : context + '/graphicalUploadController/saveUploadSurfaceCardFile',
			method : "POST",
			// 提交参数
			params : {
				uploadSurfaceCardListStr : uploadSurfaceCardList,
				uploadAll:0
			},
			success : function(response) {
				Ext.getCmp("SelectSurfaceCardFilePanel_Id").unmask("数据保存中，请稍后...");
				var result = Ext.JSON.decode(response.responseText);
				if (result.flag == true) {
					Ext.Msg.alert('提示', "<font color=blue>上传成功</font>");
				}
				if (result.flag == false) {
					Ext.Msg.alert('提示', "<font color=red>上传失败。</font>");
				}
			},
			failure : function() {
				Ext.getCmp("SelectSurfaceCardFilePanel_Id").unmask("数据保存中，请稍后...");
				Ext.Msg.alert("提示", "【<font color=red>异常抛出 </font>】：请与管理员联系！");
			}
		});
	}
	return false;
};