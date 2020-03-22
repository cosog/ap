Ext.define('AP.view.Viewport', {
    extend: 'Ext.container.Viewport',
    id: 'frame_imivport_ids',
    layout: 'border',
    items: [{
        id: 'frame_north',
        region: 'north',
        height: 60,
        border: false,
        bodyStyle:{
        	'z-index':10
        },
//        bodyStyle: 'background-color:#4a96d9;',
        html: '<div id="imgTitle"><img id="logoImg" src="../images/logo/ytlogo.png" /><span id="bannertitle">' +viewInformation.title+ '</span>' +
        		
        		"<div id='passAndExitButton'><a href='#' id='logon_a1' onclick='resetPwdFn()'><span id='logon_a1_text'>修改密码</span></a></div> " +
        		"<div id='passAndExitButton'><a href='#' id='logon_a2' onclick='userLoginOut()'><span id='logon_a2_text'>退出</span></a></div>" +
        		"<div id='passAndExitButton'><a href='#' id='logon_a5' onclick='showHelpDocumentWinFn()'><span id='logon_a5_text'>帮助</span></a></div>" +
//        		"<div id='passAndExitButton'><a href='https://github.com/cosog/apmd' target='_blank' id='logon_a5' class='help-tip''><span id='logon_a5_text'>帮助</span><p><img src='../images/help2vm.png' width='260' /></p></a></div>" +
        		"<div id='passAndExitButton2' ><a href='#' title='全屏显示' id='logon_a3' onclick='fullscreen()'></a></div> " +
        		"<div id='passAndExitButton3' style='display:none;'><a href='#' title='退出全屏' id='logon_a4'  onclick='exitFullscreen()'></a></div> " +
        		"</div>"
   }, {
        id: 'center_ids',
        region: 'center',
        border: false,
        split: true,
        bodyStyle:{
        	'z-index':1
        },
        layout: {
            type: 'border'
        },
        defaults: {
            split: true
        },
        items: [
        	{
            id: "frame_center_ids",
            region: 'center',
            xtype: 'tabpanel',
            layout: 'fit',
            closeAction: 'destroy',
            items: [
//                {
//                    title: cosog.string.index,
//                    iconCls: 'Index',
//                    html: '<iframe id="frame_main" src="' + context + '/login/showIndex" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>'
//               }
               ],
            listeners: {
                tabchange: function (tabPanel, newCard, oldCard,obj) {
                    var tabPanel = Ext.getCmp("frame_center_ids");
                    var tabnums = tabPanel.items.getCount();
                    //alert(tabPanel.getActiveTab().id);
                    var curValues = Ext.getCmp("tabNums_Id").getValue();
                    
                    
                    var curId = newCard.id;
                    //alert(curId);
                    var modules = curId.split("_");
                    var cyrData = "";
                    if (modules.length > 2) {
                        if (curId.indexOf("diagnosis") > -1) {
                            var secondBottomTab_Id = Ext.getCmp("secondBottomTab_Id").getValue();
                            cyrData = changeTabId(curId, secondBottomTab_Id);
                        } else if (curId.indexOf("compute") > -1) {
                            var productBottomTab_Id = Ext.getCmp("productBottomTab_Id").getValue();
                            cyrData = changeTabId(curId, productBottomTab_Id);

                        } else if (curId.indexOf("image") > -1) {
                            var imageBottomTab_Id = Ext.getCmp("imageBottomTab_Id").getValue();
                            cyrData = changeTabId(curId, imageBottomTab_Id);
                        }
                        addPanelEps(curId, cyrData, curId);
                        //alert("21");
                    } else {
                        addPanelEps(curId, curId, curId);
                        //alert("22");
                    }
                    
                    Ext.getCmp("tabNums_Id").setValue(tabnums);
                    
                    
                    
                    
                    
                    
//                    if (curValues == tabnums) {
//                        var curId = newCard.id;
//                        //alert(curId);
//                        var modules = curId.split("_");
//                        var cyrData = "";
//                        if (modules.length > 2) {
//                            if (curId.indexOf("diagnosis") > -1) {
//                                var secondBottomTab_Id = Ext.getCmp("secondBottomTab_Id").getValue();
//                                cyrData = changeTabId(curId, secondBottomTab_Id);
//                            } else if (curId.indexOf("compute") > -1) {
//                                var productBottomTab_Id = Ext.getCmp("productBottomTab_Id").getValue();
//                                cyrData = changeTabId(curId, productBottomTab_Id);
//
//                            } else if (curId.indexOf("image") > -1) {
//                                var imageBottomTab_Id = Ext.getCmp("imageBottomTab_Id").getValue();
//                                cyrData = changeTabId(curId, imageBottomTab_Id);
//                            }
//                            addPanelEps(curId, cyrData, curId);
//                            //alert("21");
//                        } else {
//                            addPanelEps(curId, curId, curId);
//                            //alert("22");
//                        }
//                    } else {
//                        Ext.getCmp("tabNums_Id").setValue(tabnums);
//                    }
                },
                delay: 300
            }
      }]
   },{
	   region: 'west',
       border: false,
       layout: 'border',
       collapsible: true,
       split: true,
       flex:0.11,
       items:[{
//    	   iconCls: 'zuzhi',
//           title: cosog.string.orgNav,
           region: 'south',
           height:'50%',
           layout: 'fit',
           border: false,
           id: 'frame_west',
           split: true,
           hidden: false,
           collapsible: true,
           autoDestroy: true,
           items: [{
               xtype: 'iframeView'
           }]
       },{
    	   id: 'MainModuleShow_Id',
           region: 'center',
//           iconCls: 'gongneng',
//           title: cosog.string.navPanel,
           split: false,
           height:'50%',
           collapsible: false,
           layout: 'fit',
           border: false,
           autoDestroy: true
       }]
   },
   {
        id: 'frame_south',
        region: 'south',
        xtype: "panel",
        border: false,
        hidden:true,
        bodyStyle: 'background-color:#FBFBFB;',
        html: "<div id=\"footer\">" + viewInformation.copy+"&nbsp;<a href='"+viewInformation.linkaddress+"' target='_blank'>"+viewInformation.linkshow+"</a> "+"</div>",
        height: 30
   }]
});

function fullscreen(){
	document.getElementById('passAndExitButton2').style.display='none';
	document.getElementById('passAndExitButton3').style.display='';
//    var elem=document.body;
//    if(elem.webkitRequestFullScreen){
//        elem.webkitRequestFullScreen();   
//    }else if(elem.mozRequestFullScreen){
//        elem.mozRequestFullScreen();
//    }else if(elem.requestFullScreen){
//        elem.requestFullscreen();
//    }else{
//        //浏览器不支持全屏API或已被禁用
//    }
	var el = document.documentElement;
    var rfs = el.requestFullScreen || el.webkitRequestFullScreen || el.mozRequestFullScreen || el.msRequestFullScreen;
    if (typeof rfs != "undefined" && rfs) {
        rfs.call(el);
    } else if (typeof window.ActiveXObject != "undefined") {
        var wscript = new ActiveXObject("WScript.Shell");
        if (wscript != null) {
            wscript.SendKeys("{F11}");
        }
    }
}
function exitFullscreen(){
	document.getElementById('passAndExitButton3').style.display='none';
	document.getElementById('passAndExitButton2').style.display='';
	
    var elem=document;
    if(elem.webkitCancelFullScreen){
        elem.webkitCancelFullScreen();    
    }else if(elem.mozCancelFullScreen){
        elem.mozCancelFullScreen();
    }else if(elem.cancelFullScreen){
        elem.cancelFullScreen();
    }else if(elem.exitFullscreen){
        elem.exitFullscreen();
    }else{
        //浏览器不支持全屏API或已被禁用
    }
}

//重置密码
function resetPwdFn() {
    var showResetPwdWin = Ext.create("AP.view.user.SysUserEditPwdWin");
    showResetPwdWin.show();
    return false;
}

//帮助文档窗口
function showHelpDocumentWinFn() {
//    var HelpDocumentWin = Ext.create("AP.view.help.HelpDocumentWin");
//    HelpDocumentWin.show();
	var tabPanel = Ext.getCmp("frame_center_ids");
	var getTabId = tabPanel.getComponent("HelpDocPanel");
	if(!getTabId){
		tabPanel.add(Ext.create("AP.view.help.HelpDocPanel", {
            id: 'HelpDocPanel',
            closable: true,
            iconCls: 'Help',
            closeAction: 'destroy',
            title: '帮助',
            listeners: {
                afterrender: function () {
                    //all_loading.hide();
                },
                delay: 150
            }
        })).show();
		
		Ext.Ajax.request({
    		method:'POST',
    		url:context + '/helpDocController/getHelpDocHtml',
    		success:function(response) {
    			var p =Ext.getCmp("HelpDocPanel_Id");
//    			p.body.update(response.responseText.replace(/.\/Image/g,"..\/images"));
    			p.body.update(response.responseText);
    		},
    		failure:function(){
    			Ext.MessageBox.alert("信息","请求失败");
    		},
    		params: {
            }
    	}); 
		
	}
	tabPanel.setActiveTab("HelpDocPanel");
	
	
	
    return false;
}

function mOver(obj) {
    var obj_ = obj;
    obj_.style.color = 'blue';
}

function mOut(obj) {
    var obj_ = obj;
    obj_.style.color = '';
}

function changeTabId(val, id) {
    var data_ = val.split("_");
    var tabId_ = "";
    Ext.Array.each(data_,
        function (name, index, countriesItSelf) {
            var str_ = name;
            if (index == countriesItSelf.length - 1) {
                if (id != "") {
                    tabId_ += id;
                } else {
                    tabId_ += str_;
                }
            } else {
                tabId_ += str_ + "_";
            }

        });
    return tabId_;
}