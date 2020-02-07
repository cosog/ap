Ext.define("AP.view.upload.FileUploadPanelView", {
    extend: 'Ext.panel.Panel',
    alias: 'widget.fileUploadPanelView',
    layout: 'fit',
    id: 'FileUploadPanelView_Id',
    border: false,
    initComponent: function () {
        var me = this;
        var FileTypeCombox = Ext.create(
            'Ext.form.field.ComboBox', {
                fieldLabel: '类型',
                mode: 'local', //远程模式  
                id: 'CName',
                name: 'CName',
                anchor: '95%',
                store: new Ext.data.SimpleStore({
                    fields: ['value', 'text'],
                    data: [['doc', 'doc'], ['pdf', 'pdf'],
            ['html', 'html'], ['ppt', 'ppt'], ['xls', 'xls'], ['txt', 'txt'], ['rtf', 'rtf']]
                }),
                displayField: 'text',
                valueField: 'value',
                emptyText: '必须选择文档类型',
                blankText: '必须选择文档类型',
                loadingText: '正在加载文档类型信息', //加载数据时显示的提示信息  
                typeAhead: true,
                allowBlank: true,
                triggerAction: 'all',
                listeners: {
                    select: function () {}
                }
            });
        var uploadForm = Ext.create('Ext.form.Panel', {
            baseCls: 'x-plain',
            id: 'form-panel',
            fileUpload: true,
            width: 300,
            frame: true,
            autoHeight: true,
            bodyStyle: 'padding: 10px 10px 0 10px;',
            labelWidth: 50,
            defaults: {
                anchor: '95%',
                allowBlank: false,
                msgTarget: 'side'
            },
            items: [FileTypeCombox, {
                    xtype: 'fileuploadfield',
                    id: 'form-file',
                    emptyText: '请选择需要上传文件...',
                    fieldLabel: '文件',
                    buttonText: '选择',
                    name: 'upload' // ★ from字段，对应后台java的bean属性，上传的文件字段  
                },
                {
                    xtype: 'hidden',
                    id: 'fileName',
                    emptyText: '请选择文档文件...',
                    name: 'fileName',
                    text: Ext.getCmp("form-file") //在上传这个框中，getCmp可以获取整条路径的最后的名称  

                  },
                {
                    xtype: 'hidden',
                    name: 'docId',
                    id: 'docId'
                   }
               ],
            buttons: [{
                text: '上传',
                handler: function () {
                    if (uploadForm.getForm().isValid()) {
                        uploadForm.getForm().submit({
                            url: path + 'upload/upload.action?Tab_staffId=' + staffId,
                            method: 'POST',
                            waitTitle: '请稍后',
                            waitMsg: '正在上传文档文件 ...',
                            success: function (fp, action) {
                                // updatedocListForUpload(action.result.docId,action.result.name,action.result.docUrl,action.result.CName,action.result.extType,action.result.state);  
                                msg('成功！', '文档文件上传成功！');
                                //msg("返回的ID呢"+action.result.docId);                                  
                                //Ext.log('上传成功。')  
                                //Ext.log(action.failure)  
                                //failure  
                                //Ext.log(action.result.upload);  
                                //Ext.log(action.result.msg);         
                                Ext.getCmp("form-file").reset(); // 指定文件字段的id清空其内容  
                                Ext.getCmp("CName").reset();
                            },
                            failure: function (fp, action) {
                                msg('失败！', '文档文件上传失败！');
                            }
                        });
                    }
                }
                }, {
                text: '重置',
                handler: function () {
                    uploadForm.getForm().reset();
                }
                }]
        });
        Ext.apply(me, {
            items: uploadForm
        });
        me.callParent(arguments);
    }
});