Ext.define("AP.view.acquisitionUnit.ProtocolExtendedFieldSelectWindow", {
    extend: 'Ext.window.Window',
    alias: 'widget.protocolExtendedFieldSelectWindow',
    id: 'ProtocolExtendedFieldSelectWindow_Id',
    layout: 'fit',
    title:loginUserLanguageResource.config,
    border: false,
    hidden: false,
    collapsible: true,
    constrainHeader:true,//True表示为将window header约束在视图中显示， false表示为允许header在视图之外的地方显示（默认为false）
//    constrain: true,
    closable: 'sides',
    closeAction: 'destroy',
    maximizable: true,
    minimizable: true,
    width: '25%',
    minWidth: 400,
    height: '90%',
    minHeight: 600,
    draggable: true, // 是否可拖曳
    modal: true, // 是否为模态窗口
    initComponent: function () {
        var me = this;
        Ext.create('AP.store.acquisitionUnit.ProtocolExtendedFieldConfigStore');
        Ext.apply(me, {
            tbar: [{
				xtype : "hidden",
				id : 'protocolExtendedFieldSelectedRow_Id',
				value:-1
			},{
				xtype : "hidden",
				id : 'protocolExtendedFieldSelectedCol_Id',
				value:-1
			},{
				xtype : "hidden",
				id : 'protocolExtendedFieldSelectedItemName_Id',
				value:''
			},{
				xtype : "hidden",
				id : 'protocolExtendedFieldType_Id',
				value:''
			},'->',{
            	xtype: 'button',
    			text: loginUserLanguageResource.save,
    			disabled:loginUserProtocolConfigModuleRight.editFlag!=1,
    			iconCls: 'save',
    			handler: function (v, o) {
    				var selectedItem='';
    				var row=Ext.getCmp('protocolExtendedFieldSelectedRow_Id').getValue();
                	var col=Ext.getCmp('protocolExtendedFieldSelectedCol_Id').getValue();
                	var protocolExtendedFieldType=Ext.getCmp('protocolExtendedFieldType_Id').getValue();
    				if(Ext.getCmp("ProtocolExtendedFieldGridPanel_Id").getSelectionModel().getSelection().length>0){
    					var record= Ext.getCmp("ProtocolExtendedFieldGridPanel_Id").getSelectionModel().getSelection()[0];
    					selectedItem=record.data.itemName;
    				}
    				
    				if(protocolExtendedFieldType==0){
    					protocolExtendedFieldConfigHandsontableHelper.hot.setDataAtCell(parseInt(row),parseInt(col),selectedItem);
    				}else if(protocolExtendedFieldType==1){
    					protocolExtendedFieldHighLowByteConfigHandsontableHelper.hot.setDataAtCell(parseInt(row),parseInt(col),selectedItem);
    				}
    				Ext.getCmp("ProtocolExtendedFieldSelectWindow_Id").close();
    			}
            }],
            layout: "border",
            items:[{
            	region: 'center',
            	layout: "fit",
                id:"protocolExtendedFieldSelectPanel_Id"
            }],
            listeners: {
                beforeclose: function ( panel, eOpts) {
                	
                },
                minimize: function (win, opts) {
                    win.collapse();
                }
            }
        });
        me.callParent(arguments);
    }
});

function createProtocolExtendedFieldGridPanelColumn(columnInfo) {
    var myArr = columnInfo;

    var myColumns = "[";
    for (var i = 0; i < myArr.length; i++) {
        var attr = myArr[i];
        var width_ = "";
        var lock_ = "";
        var hidden_ = "";
        var flex_ = "";
        if (attr.hidden == true) {
            hidden_ = ",hidden:true";
        }
        if (isNotVal(attr.lock)) {
            //lock_ = ",locked:" + attr.lock;
        }
        if (isNotVal(attr.width)) {
            width_ = ",width:" + attr.width;
        }
        if (isNotVal(attr.flex)) {
        	flex_ = ",flex:" + attr.flex;
        }
        myColumns += "{text:'" + attr.header + "',lockable:true,align:'center' "+width_+flex_;
        if (attr.dataIndex.toUpperCase() == 'id'.toUpperCase()) {
            myColumns += ",xtype: 'rownumberer',sortable : false,locked:false";
        }else {
            myColumns += hidden_ + lock_ + ",sortable : false,dataIndex:'" + attr.dataIndex + "',renderer:function(value){if(isNotVal(value)){return \"<span data-qtip=\"+(value==undefined?\"\":value)+\">\"+(value==undefined?\"\":value)+\"</span>\";}}";
        }
        myColumns += "}";
        if (i < myArr.length - 1) {
            myColumns += ",";
        }
    }
    myColumns += "]";
    return myColumns;
};