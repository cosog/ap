Ext.define('AP.controller.balanceInfo.BalanceInformationController', {
    extend: 'Ext.app.Controller',
    refs: [{
        ref: 'balanceInformationPanel',
        selector: 'balanceInformationPanel'
   }],
    init: function () {
        this.control({
            'balanceInformationPanel > toolbar button[action=delBalanceInformationAction]': {
                click: delectBalanceInformationData
            },
            'balanceInformationPanel > toolbar button[action=savedelBalanceInformationDataAction]': {
                click: savedelBalanceInformationData
            }
        })
    }
});

//删除
function delectBalanceInformationData() {
	alert("删除");
//    var pumpPanel_panel = Ext.getCmp("pumpingunitGrid_Id");
//    var pumpPanel_model = pumpPanel_panel.getSelectionModel();
//    var _record = pumpPanel_model.getSelection();
//    var delUrl = context + '/pumpingunitController/deletePumpingunit'
//    if (_record) {
//        // 提示是否删除数据
//        Ext.MessageBox.msgButtons['yes'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/accept.png'/>&nbsp;&nbsp;&nbsp;确定";
//        Ext.MessageBox.msgButtons['no'].text = "<img   style=\"border:0;position:absolute;right:50px;top:1px;\"  src=\'" + context + "/images/zh_CN/cancel.png'/>&nbsp;&nbsp;&nbsp;取消";
//        Ext.Msg.confirm("是否要删除？", "是否要删除这些被选择的数据？", function (btn) {
//            if (btn == "yes") {
//                ExtDel_ObjectInfo("pumpingunitGrid_Id", _record, "id", delUrl); // 第一个参数为面板的Id，选择的记录对象，当前删除对象的Id名称，url
//            }
//        });
//    } else {
//        Ext.Msg.alert('删除操作', '您必须选择一行数据以便删除！');
//    }
	return false;
}


//保存表格变化数据
function savedelBalanceInformationData() {
	var gridpanel = Ext.getCmp("balanceInformationGrid_Id");
	var store=Ext.getCmp("balanceInformationGrid_Id").getStore();
	var m = store.getUpdatedRecords();
	var total=store.getCount();
	var jsonArray = [];
	for(var i=0;i<total;i++){
		var model=store.getAt(i);
		if(model.dirty){
			jsonArray.push(model.data);
		}
	}
    if (jsonArray.length>0) {
    	Ext.Ajax.request({
    		method:'POST',
    		url:context + '/balanceInformationController/saveBalanceInformationData',
    		success:function(response) {
    			Ext.MessageBox.alert("信息","更新成功",function(){
    				gridpanel.getStore().load();
    				gridpanel.getStore().modified = []; 
    			});
    		},
    		failure:function(){
    			Ext.MessageBox.alert("错误","保存失败");
    		},
    		params: {
            	data: JSON.stringify(jsonArray)
            }
    	});   
    }else {
    }
    
    return false;
};

function updateBalanceSetFormData(record){
	Ext.getCmp("balanceInfoSetSccj_Id").setValue(record.data.sccj);
	Ext.getCmp("balanceInfoSetSccj_Id").setRawValue(record.data.sccj);
	Ext.getCmp("balanceInfoSetCyjxh_Id").setValue(record.data.cyjxh);
	Ext.getCmp("balanceInfoSetCyjxh_Id").setRawValue(record.data.cyjxh);
	Ext.getCmp("balanceInfoSetKtzt_Id").setValue(record.data.ktzt);
	Ext.getCmp("balanceInfoSetKtzt_Id").setRawValue(record.data.ktzt);
	Ext.getCmp("balanceInfoSetOperateTime_Id").setValue(record.data.gxsj);
	var balanceArr=[];
	if(record.data.phkwzzl!=""&&record.data.phkwzzl!=null&&record.data.phkwzzl!=undefined){
		balanceArr=record.data.phkwzzl.split(";");
	}
	for(var i=0;i<8;i++){
		if(i<balanceArr.length){
			var everyBalance=balanceArr[i].split(",");
			if(everyBalance.length==2){
//				Ext.getCmp("balanceInfoSetPhkwz"+(i+1)+"_Id").setValue(parseFloat(everyBalance[0])*100);
				Ext.getCmp("balanceInfoSetPhkwz"+(i+1)+"_Id").setValue(everyBalance[0]*100);
				Ext.getCmp("balanceInfoSetPhkzl"+(i+1)+"_Id").setValue(everyBalance[1]);
				Ext.getCmp("balanceInfoSetPhkzl"+(i+1)+"_Id").setRawValue(everyBalance[1]);
			}else{
				Ext.getCmp("balanceInfoSetPhkwz"+(i+1)+"_Id").setValue("");
				Ext.getCmp("balanceInfoSetPhkzl"+(i+1)+"_Id").setValue("");
				Ext.getCmp("balanceInfoSetPhkzl"+(i+1)+"_Id").setRawValue("");
			}
		}else{
			Ext.getCmp("balanceInfoSetPhkwz"+(i+1)+"_Id").setValue("");
			Ext.getCmp("balanceInfoSetPhkzl"+(i+1)+"_Id").setValue("");
			Ext.getCmp("balanceInfoSetPhkzl"+(i+1)+"_Id").setRawValue("");
		}
	}
	return false;
}

function clearBalanceSetFormData(){
	Ext.getCmp("balanceInfoSetSccj_Id").setValue("");
	Ext.getCmp("balanceInfoSetSccj_Id").setRawValue("");
	Ext.getCmp("balanceInfoSetCyjxh_Id").setValue("");
	Ext.getCmp("balanceInfoSetCyjxh_Id").setRawValue("");
	Ext.getCmp("balanceInfoSetKtzt_Id").setValue("");
	Ext.getCmp("balanceInfoSetKtzt_Id").setRawValue("");
	Ext.getCmp("balanceInfoSetOperateTime_Id").setValue("");
	for(var i=0;i<8;i++){
		Ext.getCmp("balanceInfoSetPhkwz"+(i+1)+"_Id").setValue("");
		Ext.getCmp("balanceInfoSetPhkzl"+(i+1)+"_Id").setValue("");
		Ext.getCmp("balanceInfoSetPhkzl"+(i+1)+"_Id").setRawValue("");
		
	}
	return false;
}

function updateCellValue(column,value){
	var jh=Ext.getCmp("balanceInformationSelectedJh_Id").getValue();
	var store=Ext.getCmp("balanceInformationGrid_Id").getStore();
	if(isNotVal(jh)){
		for(var i=0;i<store.getCount();i++){
			if(store.getAt(i).data.jh==jh){
				store.getAt(i).set(column,value);
				break;
			}
		}
	}
	return false;
}

function getAndUpdatePhkwzzlCellValue(){
	var value="";
	for(var i=1;i<=8;i++){
		var phkwz=Ext.getCmp("balanceInfoSetPhkwz"+i+"_Id").getValue();
		var phkzl=Ext.getCmp("balanceInfoSetPhkzl"+i+"_Id").rawValue;
		if(isNotVal(phkwz)&&isNotVal(phkzl)){
			value+=parseFloat(phkwz)/100+","+phkzl+";";
		}
	}
	if(value.length>0){
		value=value.substring(0,value.length-1); 
	}
	updateCellValue("phkwzzl",value);
	return false;
}