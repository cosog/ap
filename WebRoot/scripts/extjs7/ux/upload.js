//*****************************************上传的公共js***************************************************************//   
  
/**  
 
 * 约定:types为调用时传来的参数.形式为jsp-gig-png  
 
 *      uploadid为上传后要填充路径的控件id  
 
 *      上传的属性均为upload  
 
 * 功能:页面调用openUpload("","");方法即可  
 
 */  
  
//...允许上传的后缀名   
  
var types = "";   
  
  
  
//...上传后填充控件的id   
  
var uploadid = "";   
  
  
  
function openUpload(type,id){   
  
    types = type;   
  
    uploadid = id;   
  
    winUpload.show();   
  
  }   
  
  
  
var formUpload = new Ext.form.FormPanel({   
  
    baseCls: 'x-plain',   
    defaultType: 'textfield',   
    items: [{   
      xtype: 'textfield',   
  iframe : true,
      fieldLabel: '文 件',   
  
      name: 'upload',   
  
      inputType: 'file',   
  
      allowBlank: false,   
  
      blankText: '请上传文件',   
  
      anchor: '90%'  // anchor width by percentage   
  
    }]   ,
  	buttons : [{
										id : 'addFormUser_Id',
										xtype : 'button',
										text : '保存',
										iconCls : 'save'
									
									}, {
										text : '取消',
										width : 40,
										iconCls : 'cancel',
										handler : function() {
										}
									}]
  });   
  
  
  
var winUpload = new Ext.Window({   
    title: '资源上传',   
   width : 500,
   height:300,
    shadow : 'sides', 
    layout: 'fit',   
    plain:true,   
    bodyStyle:'padding:5px;',   
    items: formUpload
  });   