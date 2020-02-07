var global = {};
global.wellMap = {};

//golbal.wellMap.success = true;
//golbal.wellMap.welltotals = 0;
//golbal.wellMap.imgScale = 1;
//
//golbal.wellMap.list = {};
//golbal.wellMap.list.wellName = "";
//golbal.wellMap.list.arcFillColor = "";
//golbal.wellMap.list.arcX = 50;
//golbal.wellMap.list.arcY = 50;
//golbal.wellMap.list.imgScale = 1;
//golbal.wellMap.list.dataList = [];


global.wellMap.mapFile;
global.wellMap.sample = {
    "success": true,
    "welltotals": 1237,
    "imgScale": 1,
    "centerX": 116.40779,
    "centerY": 39.902187,
    "wellName": "新01-001",
    "dataType": 2,
                "list": [
                {
                    "wellName": "新01-001",
                    "arcFillColor": "#FF0000",
                    "arcX": "116.40779",
                    "arcY": "39.902187",
                    "imgScale": "2",
                    "dataList": [{
                        "key": "单位名称",
                        "value": "采油一队"
                }, {
                        "key": "井名",
                        "value": "新01-001"
                }, {
                        "key": "当前平衡度",
                        "value": "125"
                }, {
                        "key": "调整距离",
                        "value": "0.1"
                }, {
                        "key": "平衡状态",
                        "value": "严重过平衡"
                }, {
                        "key": "产液量(t/d)",
                        "value": "4.7"
                }, {
                        "key": "产油量(t/d)",
                        "value": "2.54"
                }, {
                        "key": "含水率",
                        "value": "46"
                }, {
                        "key": "工况名称",
                        "value": "充满不足"
                }]
            }
                , {
                    "wellName": "新01-002",
                    "arcFillColor": "#FF0000",
                    "arcX": "200",
                    "arcY": "200",
                    "imgScale": "2",
                    "dataList": [{
                        "key": "单位名称",
                        "value": "采油一队"
                }, {
                        "key": "井名",
                        "value": "新01-001"
                }, {
                        "key": "当前平衡度",
                        "value": "125"
                }, {
                        "key": "调整距离",
                        "value": "0.1"
                }, {
                        "key": "平衡状态",
                        "value": "严重过平衡"
                }, {
                        "key": "产液量(t/d)",
                        "value": "4.7"
                }, {
                        "key": "产油量(t/d)",
                        "value": "2.54"
                }, {
                        "key": "含水率",
                        "value": "46"
                }, {
                        "key": "工况名称",
                        "value": "充满不足"
                }]
            }
            ]
};
global.wellMap.arr = [];
global.wellMap.arrStruct = {
    wellName: "1-1",
    arcFillColor: "",
    arcX: 0,
    arcY: 0,
    arcXTemp: 0,
    arcYTemp: 0,
    imgScale: 1,
    arcRadius: 8, //半径
    arcStartAngle: 0, //起始角度        
    arcEndAngle: 2 * Math.PI, //终止角度
    dataList: [{
        key: "",
        value: ""
}]
};

global.wellMap.container = {
    ref: null,
    ctx: null,
    event: {},
    css: {}
};
global.wellMap.tip = {
    ref: null,
    ctx: null,
    event: {},
    css: {}
};
global.wellMap.subTip = {
    ref: null,
    ctx: null,
    event: {},
    css: {}
};
global.wellMap.canvasMap = {
    ref: null,
    ctx: null,
    event: {},
    hover: -1,
    css: {}
};
global.wellMap.canvasIcon = {
    ref: null,
    ctx: null,
    event: {},
    hover: -1,
    css: {}
};
global.wellMap.canvasColorBar = {
    ref: null,
    ctx: null,
    event: {},
    css: {}
};
//////////////////////////////////////////css//////////////////////////////////////////////////////////////////////////////////////////////////
global.wellMap.tip.css = {
    floatTipSpacingX: 20, //tip到中心坐标X轴-arc半径后间距
    floatTipSpacingY: 20, //tip到中心坐标Y轴-arc半径后间距

    BackgroundColor: '#a58', //
    LineWidth: 1,
    LineColor: '#000000',
    Font: 'italic bold 1em serif'
};
global.wellMap.canvasMap.css = {
    fontHeight: 0.75, //字体 em
    fontType: 'sans-serif', //字体类型
    fontColor: '#000000', //字体颜色
    
    font:"12px/20px arial, sans-serif",

    nameSpacingX: 8, //名称间距
    nameSpacingY: 8, //名称间距

    strokeArcLineWidth: 1, //arc描边宽度
    strokeArclineColor: '#000000', //arc描边颜色

};
global.wellMap.canvasIcon.css = {
    strokeArcLineWidth: 2, //描边线宽,最小值为1,不要设置为0
    arcAddFillColor: 'rgba(248, 21, 21, 0.85)', //填充颜色
    arcAddHoverFillColor: 'rgba(7, 234,234, 0.85)', //鼠标经过填充颜色

    arcMinusFillColor: 'rgba(15, 144, 45, 0.88)', //填充颜色
    arcMinusHoverFillColor: 'rgba(240, 111, 210, 0.88)', //鼠标经过填充颜色

    strokeAddLineWidth: 3, //+ 线宽度
    strokeAddLineColor: '#000000', //描边线颜色

    strokeMinusLineWidth: 3, //- 线宽度    
    strokeMinusLineColor: '#000000', //描边线颜色
    backgroundColor: 'rgba(23,45,67,0)', //背景色 

    arcAddX: 0, //计算得到坐标
    arcAddY: 0, //计算得到坐标

    arcMinusX: 0, //计算得到坐标
    arcMinusY: 0, //计算得到坐标

    spacingY: 60, //+ - 图标Y轴中心间距
    lineLength: 14, //+ — 图标长度
    radius: 20, //圆半径

    floatTop: 50, //浮动距离
    floatRight: 10, //浮动距离
    floatLeft: 0, //预留，暂未用
    floatBottom: 0 //预留，暂未用
};
global.wellMap.canvasColorBar.css = {
    firstColor: 'rgb(0,228,0)', //正常状态颜色
    secondColor: 'rgb(255,255,0)', //早期预警状态颜色
    thirdColor: 'rgb(255,128,0)', //中期预警状态颜色
    founthColor: 'rgb(255,0,0)', //监控中心级报警状态颜色
    fifthColor: 'rgb(153,0,76)', //调度中心级报警状态颜色
    sixthColor: 'rgb(126,0,35)', //指挥中心级报警状态颜色
    lineWidth: 2, //描边线宽 
    strokeLineColor: 'rgba(10, 10, 10, 0.9)', //描边颜色

    fontHeight: 0.8, //字体 em
    fontType: 'sans-serif', //字体类型
    fontColor: '#000000', //字体颜色

    backgroundColor: 'rgba(177, 20, 62, 0)', //背景色

    spacingY: 10, //文字与矩形间距
    rectWidth: 40, //矩形宽
    rectHeight: 20, //矩形高

    floatTop: 0, //预留,暂未用
    floatRight: 100, //浮动距离
    floatLeft: 0, //预留,暂未用
    floatBottom: 20, //浮动距离

    defaultFloatRight: 100 //缺省的FloatRight,用于恢复FloatRight默认值,floatRight根据容器大小动态调整
};
/////////////////////////////////////////////////////////全局变量/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
global.wellMap.x, global.wellMap.y, global.wellMap.radius;
global.wellMap.imgXTemp = 0, global.wellMap.imgYTemp = 0;
global.wellMap.img, global.wellMap.imgIsLoaded = false, global.wellMap.imgX = 0, global.wellMap.imgY = 0, global.wellMap.imgScale = 1; //图片
global.wellMap.centerXOffset, global.wellMap.centerYOffset;
global.wellMap.wellName;
global.wellMap.wellData = [];
global.wellMap.dataType = 1;

global.wellMap.flag = {
    add: false,
    delete: false,
    drag: false,
    fullWindows: false,
    locateWellMark: -1,
    tipWellMark: -1,
    dragWellStatus: false
};
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
global.wellMap.addWell = function () {
    global.wellMap.flag.add = true;
    global.wellMap.flag.delete = false;
    global.wellMap.flag.drag = false;
};

global.wellMap.deleteWell = function () {
    global.wellMap.flag.add = false;
    global.wellMap.flag.delete = true;
    global.wellMap.flag.drag = false;
};

global.wellMap.dragWell = function () {
    global.wellMap.flag.add = false;
    global.wellMap.flag.delete = false;
    global.wellMap.flag.drag = true;
};

global.wellMap.saveWell = function () {

    with(global.wellMap) {

        imgXTemp = imgX;
        imgYTemp = imgY;
        imgX = 0;
        imgY = 0;

        for (var i = 0; i < arr.length; i++) {
            arr[i].arcXTemp = (arr[i].arcX + imgX - imgXTemp) / imgScale;
            arr[i].arcYTemp = (arr[i].arcY + imgY - imgYTemp) / imgScale;
        }
        imgX = imgXTemp;
        imgY = imgYTemp;
        imgXTemp = 0;
        imgYTemp = 0;

        if (flag.locateWellMark >= 0) {
            wellName = arr[flag.locateWellMark].wellName;
        }


        //当后台拖动井图标或ctrl+鼠标点击时，即井的位置发生变化触发global.wellMap.saveWell()函数
        //当前输出井名    global.wellMap.wellName
        //当前井数组下标    global.wellMap.flag.locateWellMark
        //当前井输出x坐标   global.wellMap.arr[global.wellMap.flag.locateWellMark].arcXTemp
        //当前井输出y坐标   global.wellMap.arr[global.wellMap.flag.locateWellMark].arcYTemp
        //全部换算完成的x坐标  global.wellMap.arr数组arcXTemp
        //全部换算完成的Y坐标  global.wellMap.arr数组arcYTemp
        //        alert(global.wellMap.wellName + "," + global.wellMap.arr[global.wellMap.flag.locateWellMark].arcXTemp + "," + global.wellMap.arr[global.wellMap.flag.locateWellMark].arcYTemp);
        updateWellCoordCellValue(global.wellMap.wellName, global.wellMap.arr[global.wellMap.flag.locateWellMark].arcXTemp, global.wellMap.arr[global.wellMap.flag.locateWellMark].arcYTemp);

    };

};
global.wellMap.default = function () {
    global.wellMap.flag.add = false;
    global.wellMap.flag.delete = false;
    global.wellMap.flag.drag = false;
};

global.wellMap.init = function (mapFile, divID, wellDataJSON) {

    global.wellMap.arr.splice(0, global.wellMap.arr.length);
    global.wellMap.imgX = 0;
    global.wellMap.imgY = 0;
    global.wellMap.flag.dragWellStatus = false;

    global.wellMap.mapFile = mapFile;
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    if (wellDataJSON.imgScale != undefined) {
        global.wellMap.imgScale = wellDataJSON.imgScale;
    }
        global.wellMap.wellName = wellDataJSON.wellName;
        global.wellMap.dataType = wellDataJSON.dataType;
        global.wellMap.wellData = wellDataJSON.list;
    
    ////////////////////测试数据,测试接口无数据时,注释上面4条记录,打开下面3条记录//////////////////////////////////////////////////////////
//        global.wellMap.imgScale = global.wellMap.sample.imgScale;
//        global.wellMap.wellName = global.wellMap.sample.wellName;
//        global.wellMap.dataType = global.wellMap.sample.dataType;
//        global.wellMap.wellData = global.wellMap.sample.list
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    //
    if (global.wellMap.wellData != undefined) {
        for (var i = 0; i < global.wellMap.wellData.length; i++) {
            global.wellMap.arr.push({
                wellName: "1-1",
                arcFillColor: "",
                arcX: 0,
                arcY: 0,
                arcXTemp: 0,
                arcYTemp: 0,
                imgScale: 1,
                arcRadius: 5, //半径
                arcStartAngle: 0, //起始角度        
                arcEndAngle: 2 * Math.PI, //终止角度
                dataList: [{
                    key: "",
                    value: ""
      }]
            });

            global.wellMap.arr[i].wellName = global.wellMap.wellData[i].wellName;
            global.wellMap.arr[i].arcFillColor = global.wellMap.wellData[i].arcFillColor;
            global.wellMap.arr[i].arcX = global.wellMap.wellData[i].arcX;
            global.wellMap.arr[i].arcY = global.wellMap.wellData[i].arcY;
            global.wellMap.arr[i].arcXTemp = global.wellMap.wellData[i].arcX;
            global.wellMap.arr[i].arcYTemp = global.wellMap.wellData[i].arcY;
            global.wellMap.arr[i].imgScale = global.wellMap.wellData[i].imgScale;
            global.wellMap.arr[i].dataList = global.wellMap.wellData[i].dataList;
        };
    };

    if (global.wellMap.dataType == 2) {
        global.wellMap.flag.add = false;
        global.wellMap.flag.delete = false;
        global.wellMap.flag.drag = true;

    } else {
        global.wellMap.flag.add = false;
        global.wellMap.flag.delete = false;
        global.wellMap.flag.drag = false;
    };

    for (var i = 0; i < global.wellMap.arr.length; i++) {
        if (global.wellMap.wellName == global.wellMap.arr[i].wellName) {
            global.wellMap.flag.locateWellMark = i;
            break;
        };
    }


    $(divID).append("<div id =\"global_wellMap_wellMapBoxFlexID\" class =\"global-wellMap-flexbox-container\"></div>");
    $("#global_wellMap_wellMapBoxFlexID").append("<div id=\"global_wellMap_divContainerID\" style=\"position:relative\"  ></div>");
    $("#global_wellMap_divContainerID").append("<canvas id=\"global_wellMap_canvasMapID\" style=\"position:absolute;z-index=2\"></canvas>");
    $("#global_wellMap_divContainerID").append("<canvas id=\"global_wellMap_canvasIconID\" style=\"overflow:auto;position:absolute;z-index=-1;\"></canvas>");
    $("#global_wellMap_divContainerID").append("<canvas id=\"global_wellMap_canvasColorBarID\" style=\"overflow:auto;position:absolute;z-index=-2\"></canvas>");
    $("#global_wellMap_divContainerID").append("<div id=\"global_wellMap_divTipID\" class=\"global-wellMap-tip\" style=\"position:absolute;z-index:5;\">");
    $("#global_wellMap_divTipID").append("<div id=\"global_wellMap_subDivTipID\" class=\"global-wellMap-subTip\"></div>");

    global.wellMap.container.ref = document.getElementById('global_wellMap_divContainerID'); //获取引用
    global.wellMap.canvasMap.ref = document.getElementById('global_wellMap_canvasMapID'); //获取引用
    global.wellMap.canvasIcon.ref = document.getElementById('global_wellMap_canvasIconID'); //获取引用
    global.wellMap.canvasColorBar.ref = document.getElementById('global_wellMap_canvasColorBarID'); //获取引用
    global.wellMap.tip.ref = document.getElementById('global_wellMap_divTipID'); //获取引用
    global.wellMap.subTip.ref = document.getElementById('global_wellMap_subDivTipID'); //获取引用

    global.wellMap.canvasMap.ctx = global.wellMap.canvasMap.ref.getContext('2d'); //获取引用环境
    global.wellMap.canvasIcon.ctx = global.wellMap.canvasIcon.ref.getContext('2d'); //获取引用环境
    global.wellMap.canvasColorBar.ctx = global.wellMap.canvasColorBar.ref.getContext('2d'); //获取引用环境

    window.addEventListener('resize', global.wellMap.resizeCanvas, false);
    //    window.addEventListener('mousemove', window.onmousemove, false);
    //    window.addEventListener('mouseup', window.onmouseup, false);

    global.wellMap.canvasMap.ref.addEventListener('mousemove', global.wellMap.canvasMap.event.mouseMove, false);
    global.wellMap.canvasMap.ref.addEventListener('mouseup', global.wellMap.canvasMap.event.mouseUp, false);
    global.wellMap.canvasMap.ref.addEventListener('mousedown', global.wellMap.canvasMap.event.mouseDown, false);
    global.wellMap.canvasMap.ref.addEventListener('wheel', global.wellMap.canvasMap.event.wheel, false);
    global.wellMap.canvasMap.ref.addEventListener('click', global.wellMap.canvasMap.event.mouseClick, false);

    global.wellMap.canvasIcon.ref.addEventListener('mousemove', global.wellMap.canvasIcon.event.mouseMove, false);
    global.wellMap.canvasIcon.ref.addEventListener('mouseup', global.wellMap.canvasIcon.event.mouseUp, false);
    global.wellMap.canvasIcon.ref.addEventListener('mousedown', global.wellMap.canvasIcon.event.mouseDown, false);


    global.wellMap.canvasMap.ref.addEventListener('dblclick', function () {
        var docElm = document.documentElement;
        if (docElm.requestFullscreen) {
            docElm.requestFullscreen();
        } else if (docElm.msRequestFullscreen) {
            docElm.msRequestFullscreen();
        } else if (docElm.mozRequestFullScreen) {
            docElm.mozRequestFullScreen();
        } else if (docElm.webkitRequestFullScreen) {
            docElm.webkitRequestFullScreen();
        }
    }, false);

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    global.wellMap.canvasMap.ref.width = global.wellMap.container.ref.clientWidth;
    global.wellMap.canvasMap.ref.height = global.wellMap.container.ref.clientHeight;
    with(global.wellMap) {
        if ((global.wellMap.flag.locateWellMark < global.wellMap.arr.length) && (global.wellMap.flag.locateWellMark >= 0)) {
            imgXTemp = imgX;
            imgYTemp = imgY;

            centerXOffset = arr[flag.locateWellMark].arcX * imgScale - canvasMap.ref.clientWidth / 2;
            centerYOffset = arr[flag.locateWellMark].arcY * imgScale - canvasMap.ref.clientHeight / 2;

            imgX = -centerXOffset;
            imgY = -centerYOffset;
            for (var i = 0; i < arr.length; i++) {
                arr[i].arcX = arr[i].arcX * imgScale + imgX - imgXTemp;
                arr[i].arcY = arr[i].arcY * imgScale + imgY - imgYTemp;
            };
        };
        //icon画布位置、大小计算
        canvasIcon.ref.width = 2 * canvasIcon.css.radius + 2 * canvasIcon.css.strokeArcLineWidth;
        canvasIcon.ref.height = canvasIcon.css.spacingY + 2 * canvasIcon.css.radius + 2 * canvasIcon.css.strokeArcLineWidth;
        canvasIcon.ref.style.top = canvasIcon.css.floatTop + "px";
        //        canvasIcon.ref.style.left = container.ref.clientWidth - canvasIcon.ref.offsetWidth - canvasIcon.css.floatRight + "px";
        canvasIcon.ref.style.left = container.ref.clientWidth - canvasIcon.ref.clientWidth - canvasIcon.css.floatRight + "px";

        //        canvasIcon.ref.style.top = container.ref.offsetTop + canvasIcon.css.floatTop + "px";
        //        canvasIcon.ref.style.left = container.ref.offsetLeft + container.ref.clientWidth - canvasIcon.ref.offsetWidth - canvasIcon.css.floatRight + "px";
        canvasIcon.css.arcAddX = canvasIcon.ref.width / 2;
        canvasIcon.css.arcAddY = canvasIcon.css.radius + canvasIcon.css.strokeArcLineWidth;
        canvasIcon.css.arcMinusX = canvasIcon.ref.width / 2;
        canvasIcon.css.arcMinusY = canvasIcon.ref.height - canvasIcon.css.radius - canvasIcon.css.strokeArcLineWidth;
        canvasIcon.ref.style.backgroundColor = canvasIcon.css.backgroundColor;

        //colorBar画布位置、大小计算

        canvasColorBar.ref.width = 4 * canvasColorBar.css.rectWidth + canvasColorBar.css.lineWidth;

        canvasColorBar.css.floatRight = canvasColorBar.css.defaultFloatRight; //floatRight恢复默认值
        if (canvasColorBar.ref.width + 2 * canvasColorBar.css.floatRight > container.ref.clientWidth) { //当屏幕过小时canvasColorBar居中

            canvasColorBar.css.floatRight = (container.ref.clientWidth - canvasColorBar.ref.width) / 2;
        };
        canvasColorBar.ref.height = canvasColorBar.css.rectHeight + 2 * canvasColorBar.css.spacingY + canvasColorBar.css.fontHeight * 16;
        canvasColorBar.ref.style.top = container.ref.clientHeight - canvasColorBar.ref.height - canvasColorBar.css.floatBottom + "px";
        //         canvasColorBar.ref.style.top = container.ref.offsetTop + container.ref.clientHeight - canvasColorBar.ref.height - canvasColorBar.css.floatBottom + "px";
        //        canvasColorBar.ref.style.left = container.ref.offsetLeft + container.ref.clientWidth - canvasColorBar.ref.width - canvasColorBar.css.floatRight + "px";
        canvasColorBar.ref.style.left = container.ref.clientWidth - canvasColorBar.ref.width - canvasColorBar.css.floatRight + "px";
        canvasColorBar.ref.style.backgroundColor = canvasColorBar.css.backgroundColor;
        //      
    };
    global.wellMap.loadImg();
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    //    global.wellMap.resizeCanvas();


};
global.wellMap.updateWellData = function (wellDataJSON) {
    global.wellMap.arr.splice(0, global.wellMap.arr.length);
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        global.wellMap.dataType = wellDataJSON.dataType;
        global.wellMap.wellData = wellDataJSON.list;
    ////////////////////测试数据,测试接口无数据时,注释上面2条记录,打开下面3条记录//////////////////////////////////////////////////////////
//    global.wellMap.dataType = global.wellMap.sample.dataType;
//    global.wellMap.wellData = global.wellMap.sample.list
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    for (var i = 0; i < global.wellMap.wellData.length; i++) {
        global.wellMap.arr.push({
            wellName: "1-1",
            arcFillColor: "",
            arcX: 0,
            arcY: 0,
            arcXTemp: 0,
            arcYTemp: 0,
            imgScale: 1,
            arcRadius: 5, //半径
            arcStartAngle: 0, //起始角度        
            arcEndAngle: 2 * Math.PI, //终止角度
            dataList: [{
                key: "",
                value: ""
      }]
        });

        global.wellMap.arr[i].wellName = global.wellMap.wellData[i].wellName;
        global.wellMap.arr[i].arcFillColor = global.wellMap.wellData[i].arcFillColor;
        global.wellMap.arr[i].arcX = global.wellMap.wellData[i].arcX;
        global.wellMap.arr[i].arcY = global.wellMap.wellData[i].arcY;
        global.wellMap.arr[i].arcXTemp = global.wellMap.wellData[i].arcX;
        global.wellMap.arr[i].arcYTemp = global.wellMap.wellData[i].arcY;
        global.wellMap.arr[i].imgScale = global.wellMap.wellData[i].imgScale;
        global.wellMap.arr[i].dataList = global.wellMap.wellData[i].dataList;
    };

    with(global.wellMap) {
        for (var i = 0; i < arr.length; i++) {
            arr[i].arcX = arr[i].arcX * imgScale + imgX;
            arr[i].arcY = arr[i].arcY * imgScale + imgY;
        };

        for (var i = 0; i < arr.length; i++) {
            if (wellName == arr[i].wellName) {
                flag.locateWellMark = i;
                break;
            } else {
                flag.locateWellMark = 0;
                wellName = arr[0].wellName;
            }

        }
    }

    if (global.wellMap.dataType == 2) {
        global.wellMap.flag.add = false;
        global.wellMap.flag.delete = false;
        global.wellMap.flag.drag = true;

    } else {
        global.wellMap.flag.add = false;
        global.wellMap.flag.delete = false;
        global.wellMap.flag.drag = false;
    };
    global.wellMap.drawImage();
};
global.wellMap.locateWell = function (wellName) {
    global.wellMap.wellName = wellName;
    for (var i = 0; i < global.wellMap.arr.length; i++) {
        if (global.wellMap.wellName == global.wellMap.arr[i].wellName) {
            global.wellMap.flag.locateWellMark = i;
            break;
        };
    }
    global.wellMap.canvasMap.ref.width = global.wellMap.container.ref.clientWidth;
    global.wellMap.canvasMap.ref.height = global.wellMap.container.ref.clientHeight;
    if (global.wellMap.flag.locateWellMark >= 0 && global.wellMap.flag.locateWellMark < global.wellMap.arr.length && global.wellMap.arr[global.wellMap.flag.locateWellMark].arcXTemp >= 0 && global.wellMap.arr[global.wellMap.flag.locateWellMark].arcYTemp >= 0) {
        with(global.wellMap) {
            if (arr[flag.locateWellMark].arcX * imgScale > canvasMap.ref.clientWidth || arr[flag.locateWellMark].arcX < 0 || arr[flag.locateWellMark].arcY * imgScale > canvasMap.ref.clientHeight || arr[flag.locateWellMark].arcY < 0) {

                imgXTemp = imgX;
                imgYTemp = imgY;
                imgX = 0;
                imgY = 0;

                for (var i = 0; i < global.wellMap.arr.length; i++) {
                    arr[i].arcX = (arr[i].arcX + imgX - imgXTemp) / imgScale;
                    arr[i].arcY = (arr[i].arcY + imgY - imgYTemp) / imgScale;
                }

                centerXOffset = arr[flag.locateWellMark].arcX * imgScale - canvasMap.ref.clientWidth / 2;
                centerYOffset = arr[flag.locateWellMark].arcY * imgScale - canvasMap.ref.clientHeight / 2;

                imgXTemp = imgX;
                imgYTemp = imgY;
                imgX = -centerXOffset;
                imgY = -centerYOffset;

                for (var i = 0; i < arr.length; i++) {
                    arr[i].arcX = arr[i].arcX * imgScale + imgX - imgXTemp;
                    arr[i].arcY = arr[i].arcY * imgScale + imgY - imgYTemp;
                };

            };
            drawImage();
        };
        with(global.wellMap) {

            setTimeout(function () {
                canvasMap.ctx.fillStyle = canvasIcon.css.arcAddHoverFillColor;
                canvasMap.ctx.beginPath();
                canvasMap.ctx.arc(arr[flag.locateWellMark].arcX, arr[flag.locateWellMark].arcY, arr[flag.locateWellMark].arcRadius * imgScale, arr[flag.locateWellMark].arcStartAngle, arr[flag.locateWellMark].arcEndAngle, true);
                canvasMap.ctx.fillText(arr[flag.locateWellMark].wellName, arr[flag.locateWellMark].arcX + canvasMap.css.nameSpacingX * imgScale, arr[flag.locateWellMark].arcY + canvasMap.css.nameSpacingY * imgScale);
                canvasMap.ctx.fill();
                canvasMap.ctx.stroke();
            }, 500);
            setTimeout(function () {
                canvasMap.ctx.fillStyle = arr[flag.locateWellMark].arcFillColor;
                canvasMap.ctx.beginPath();
                canvasMap.ctx.arc(arr[flag.locateWellMark].arcX, arr[flag.locateWellMark].arcY, arr[flag.locateWellMark].arcRadius * imgScale, arr[flag.locateWellMark].arcStartAngle, arr[flag.locateWellMark].arcEndAngle, true);
                canvasMap.ctx.fillText(arr[flag.locateWellMark].wellName, arr[flag.locateWellMark].arcX + canvasMap.css.nameSpacingX * imgScale, arr[flag.locateWellMark].arcY + canvasMap.css.nameSpacingY * imgScale);
                canvasMap.ctx.fill();
                canvasMap.ctx.stroke();
            }, 1000);
            setTimeout(function () {
                canvasMap.ctx.fillStyle = canvasIcon.css.arcAddHoverFillColor;
                canvasMap.ctx.beginPath();
                canvasMap.ctx.arc(arr[flag.locateWellMark].arcX, arr[flag.locateWellMark].arcY, arr[flag.locateWellMark].arcRadius * imgScale, arr[flag.locateWellMark].arcStartAngle, arr[flag.locateWellMark].arcEndAngle, true);
                canvasMap.ctx.fillText(arr[flag.locateWellMark].wellName, arr[flag.locateWellMark].arcX + canvasMap.css.nameSpacingX * imgScale, arr[flag.locateWellMark].arcY + canvasMap.css.nameSpacingY * imgScale);
                canvasMap.ctx.fill();
                canvasMap.ctx.stroke();
            }, 1500);
            setTimeout(function () {
                canvasMap.ctx.fillStyle = arr[flag.locateWellMark].arcFillColor;
                canvasMap.ctx.beginPath();
                canvasMap.ctx.arc(arr[flag.locateWellMark].arcX, arr[flag.locateWellMark].arcY, arr[flag.locateWellMark].arcRadius * imgScale, arr[flag.locateWellMark].arcStartAngle, arr[flag.locateWellMark].arcEndAngle, true);
                canvasMap.ctx.fillText(arr[flag.locateWellMark].wellName, arr[flag.locateWellMark].arcX + canvasMap.css.nameSpacingX * imgScale, arr[flag.locateWellMark].arcY + canvasMap.css.nameSpacingY * imgScale);
                canvasMap.ctx.fill();
                canvasMap.ctx.stroke();
            }, 2000);
        };
    } else {
        global.wellMap.drawImage();
    }
};
global.wellMap.resizeCanvas = function () {

    //地图画布大小计算
    global.wellMap.canvasMap.ref.width = global.wellMap.container.ref.clientWidth;
    global.wellMap.canvasMap.ref.height = global.wellMap.container.ref.clientHeight;

    with(global.wellMap) {

        //icon画布位置、大小计算
        canvasIcon.ref.width = 2 * canvasIcon.css.radius + 2 * canvasIcon.css.strokeArcLineWidth;
        canvasIcon.ref.height = canvasIcon.css.spacingY + 2 * canvasIcon.css.radius + 2 * canvasIcon.css.strokeArcLineWidth;
        canvasIcon.ref.style.top = canvasIcon.css.floatTop + "px";
        //        canvasIcon.ref.style.left = container.ref.clientWidth - canvasIcon.ref.offsetWidth - canvasIcon.css.floatRight + "px";
        canvasIcon.ref.style.left = container.ref.clientWidth - canvasIcon.ref.clientWidth - canvasIcon.css.floatRight + "px";

        //        canvasIcon.ref.style.top = container.ref.offsetTop + canvasIcon.css.floatTop + "px";
        //        canvasIcon.ref.style.left = container.ref.offsetLeft + container.ref.clientWidth - canvasIcon.ref.offsetWidth - canvasIcon.css.floatRight + "px";
        canvasIcon.css.arcAddX = canvasIcon.ref.width / 2;
        canvasIcon.css.arcAddY = canvasIcon.css.radius + canvasIcon.css.strokeArcLineWidth;
        canvasIcon.css.arcMinusX = canvasIcon.ref.width / 2;
        canvasIcon.css.arcMinusY = canvasIcon.ref.height - canvasIcon.css.radius - canvasIcon.css.strokeArcLineWidth;
        canvasIcon.ref.style.backgroundColor = canvasIcon.css.backgroundColor;

        //colorBar画布位置、大小计算

        canvasColorBar.ref.width = 4 * canvasColorBar.css.rectWidth + canvasColorBar.css.lineWidth;

        canvasColorBar.css.floatRight = canvasColorBar.css.defaultFloatRight; //floatRight恢复默认值
        if (canvasColorBar.ref.width + 2 * canvasColorBar.css.floatRight > container.ref.clientWidth) { //当屏幕过小时canvasColorBar居中

            canvasColorBar.css.floatRight = (container.ref.clientWidth - canvasColorBar.ref.width) / 2;
        };
        canvasColorBar.ref.height = canvasColorBar.css.rectHeight + 2 * canvasColorBar.css.spacingY + canvasColorBar.css.fontHeight * 16;
        canvasColorBar.ref.style.top = container.ref.clientHeight - canvasColorBar.ref.height - canvasColorBar.css.floatBottom + "px";
        //         canvasColorBar.ref.style.top = container.ref.offsetTop + container.ref.clientHeight - canvasColorBar.ref.height - canvasColorBar.css.floatBottom + "px";
        //        canvasColorBar.ref.style.left = container.ref.offsetLeft + container.ref.clientWidth - canvasColorBar.ref.width - canvasColorBar.css.floatRight + "px";
        canvasColorBar.ref.style.left = container.ref.clientWidth - canvasColorBar.ref.width - canvasColorBar.css.floatRight + "px";
        canvasColorBar.ref.style.backgroundColor = canvasColorBar.css.backgroundColor;

    };

    global.wellMap.loadImg();
};

global.wellMap.loadImg = function () {

    global.wellMap.img = new Image();

    global.wellMap.img.onload = function () {

        global.wellMap.imgIsLoaded = true;

        //        with(global.wellMap) {
        //            if (img.width * imgScale > canvasMap.ref.clientWidth || img.height * imgScale > canvasMap.ref.clientHeight) { //如果图像宽度或高度大于画布宽度或高度
        //                if (!(canvasMap.ref.clientWidth / img.width > canvasMap.ref.clientHeight / img.height)) { //相对更加宽,按整宽处理
        //                    imgScale = canvasMap.ref.clientWidth / img.width;
        //                    imgXTemp = imgX;
        //                    imgYTemp = imgY;
        //                    imgX = 0;
        //                    imgY = (canvasMap.ref.clientHeight - img.height * imgScale) / 2;
        //                    for (var i = 0; i < arr.length; i++) {
        //                        arr[i].arcX = arr[i].arcX * imgScale + imgX - imgXTemp;
        //                        arr[i].arcY = arr[i].arcY * imgScale + imgY - imgYTemp;
        //
        //                    };
        //                } else { //相对更加高,按整高处理
        //                    imgScale = canvasMap.ref.clientHeight / img.height;
        //                    imgXTemp = imgX;
        //                    imgYTemp = imgY;
        //                    imgX = (canvasMap.ref.clientWidth - img.width * imgScale) / 2;
        //                    imgY = 0;
        //                    for (var i = 0; i < arr.length; i++) {
        //                        arr[i].arcX = arr[i].arcX * imgScale + imgX - imgXTemp;
        //                        arr[i].arcY = arr[i].arcY * imgScale + imgY - imgYTemp;
        //
        //                    };
        //                };
        //            } else {
        //                imgXTemp = imgX;
        //                imgYTemp = imgY;
        //                imgX = (canvasMap.ref.clientWidth - img.width * imgScale) / 2; //图像X方向绘制在中间位置
        //                imgY = (canvasMap.ref.clientHeight - img.height * imgScale) / 2; //图像Y方向绘制在中间位置
        //                for (var i = 0; i < arr.length; i++) {
        //                    arr[i].arcX = arr[i].arcX * imgScale + imgX - imgXTemp;
        //                    arr[i].arcY = arr[i].arcY * imgScale + imgY - imgYTemp;
        //
        //                };
        //            };

        //
        //            for (var i = 0; i < arr.length; i++) {
        //
        //                arr[i].arcX = arr[i].arcX * imgScale + imgX - imgXTemp;
        //                arr[i].arcY = arr[i].arcY * imgScale + imgY - imgYTemp;
        //            };
        //        };
        global.wellMap.drawImage();
    };

    global.wellMap.img.src = global.wellMap.mapFile;

};

global.wellMap.drawImage = function () {
    with(global.wellMap) {
        //地图画布清除及图像加载
        canvasMap.ctx.clearRect(0, 0, canvasMap.ref.width, canvasMap.ref.height);
        //    canvasMap.ctx.drawImage(img, 0, 0, img.width, img.height, imgX, imgY, canvasMap.ref.width * imgScale, canvasMap.ref.width / img.width * img.height * imgScale);
        canvasMap.ctx.drawImage(img, 0, 0, img.width, img.height, imgX, imgY, img.width * imgScale, img.height * imgScale);

        canvasMap.ctx.font = imgScale * canvasMap.css.fontHeight + "em " + canvasMap.css.fontType;
        canvasMap.ctx.font.fontColor = canvasMap.css.fontColor;
        canvasMap.ctx.strokeStyle = 'rgba(26, 20, 177, 0.85)';
        //icon绘制
        if (canvasIcon.hover == 1) {
            canvasIcon.ctx.fillStyle = canvasIcon.css.arcAddHoverFillColor; //+图标画前赋值
        } else {
            canvasIcon.ctx.fillStyle = canvasIcon.css.arcAddFillColor; //+图标画前赋值
        };
        canvasIcon.ctx.lineWidth = canvasIcon.css.strokeArcLineWidth; //+图标画前赋值
        canvasIcon.ctx.strokeStyle = canvasIcon.css.strokeAddLineColor; //+图标画前赋值

        canvasIcon.ctx.beginPath();
        canvasIcon.ctx.arc(canvasIcon.css.arcAddX, canvasIcon.css.arcAddY, canvasIcon.css.radius, 0, 2 * Math.PI, true);
        canvasIcon.ctx.fill();
        canvasIcon.ctx.stroke();
        canvasIcon.ctx.closePath();

        canvasIcon.ctx.lineWidth = canvasIcon.css.strokeAddLineWidth;
        canvasIcon.ctx.beginPath();
        canvasIcon.ctx.moveTo(canvasIcon.css.arcAddX - canvasIcon.css.lineLength / 2, canvasIcon.css.arcAddY);
        canvasIcon.ctx.lineTo(canvasIcon.css.arcAddX + canvasIcon.css.lineLength / 2, canvasIcon.css.arcAddY);
        canvasIcon.ctx.moveTo(canvasIcon.css.arcAddX, canvasIcon.css.arcAddY - canvasIcon.css.lineLength / 2);
        canvasIcon.ctx.lineTo(canvasIcon.css.arcAddX, canvasIcon.css.arcAddY + canvasIcon.css.lineLength / 2);
        canvasIcon.ctx.stroke();
        canvasIcon.ctx.closePath();
        if (canvasIcon.hover == 2) {
            canvasIcon.ctx.fillStyle = canvasIcon.css.arcMinusHoverFillColor; //-图标画前赋值 
        } else {
            canvasIcon.ctx.fillStyle = canvasIcon.css.arcMinusFillColor; //-图标画前赋值
        };
        canvasIcon.ctx.lineWidth = canvasIcon.css.strokeArcLineWidth; //-图标画前赋值
        canvasIcon.ctx.strokeStyle = canvasIcon.css.strokeMinusLineColor; //-图标画前赋值

        canvasIcon.ctx.beginPath();
        canvasIcon.ctx.arc(canvasIcon.css.arcMinusX, canvasIcon.css.arcMinusY, canvasIcon.css.radius, 0, 2 * Math.PI, true);
        canvasIcon.ctx.fill();
        canvasIcon.ctx.stroke();

        canvasIcon.ctx.lineWidth = canvasIcon.css.strokeMinusLineWidth;
        canvasIcon.ctx.beginPath();
        canvasIcon.ctx.moveTo(canvasIcon.css.arcMinusX - canvasIcon.css.lineLength / 2, canvasIcon.css.arcMinusY);
        canvasIcon.ctx.lineTo(canvasIcon.css.arcMinusX + canvasIcon.css.lineLength / 2, canvasIcon.css.arcMinusY);
        canvasIcon.ctx.stroke();
        canvasIcon.ctx.closePath();

        if (dataType == 1) {
            //colorBar绘制
            canvasColorBar.ctx.beginPath();

            canvasColorBar.ctx.font = canvasColorBar.css.fontHeight + "em " + canvasColorBar.css.fontType;
            canvasColorBar.ctx.font.fontColor = canvasColorBar.css.fontColor;

            canvasColorBar.ctx.fillText("报警级别", 0, canvasColorBar.css.fontHeight * 16);

            canvasColorBar.ctx.closePath();
            canvasColorBar.ctx.stroke();

            canvasColorBar.ctx.beginPath();
            canvasColorBar.ctx.lineWidth = canvasColorBar.css.lineWidth;
            canvasColorBar.ctx.strokeStyle = canvasColorBar.css.strokeLineColor;

            canvasColorBar.ctx.fillStyle = canvasColorBar.css.firstColor;
            canvasColorBar.ctx.fillRect(canvasColorBar.css.lineWidth / 2, canvasColorBar.css.fontHeight * 16 + canvasColorBar.css.spacingY, canvasColorBar.css.rectWidth, canvasColorBar.css.rectHeight);
            canvasColorBar.ctx.strokeRect(canvasColorBar.css.lineWidth / 2, canvasColorBar.css.fontHeight * 16 + canvasColorBar.css.spacingY, canvasColorBar.css.rectWidth, canvasColorBar.css.rectHeight);


            canvasColorBar.ctx.fillStyle = canvasColorBar.css.secondColor;
            canvasColorBar.ctx.fillRect(canvasColorBar.css.lineWidth / 2 + canvasColorBar.css.rectWidth, canvasColorBar.css.fontHeight * 16 + canvasColorBar.css.spacingY, canvasColorBar.css.rectWidth, canvasColorBar.css.rectHeight);
            canvasColorBar.ctx.strokeRect(canvasColorBar.css.lineWidth / 2 + canvasColorBar.css.rectWidth, canvasColorBar.css.fontHeight * 16 + canvasColorBar.css.spacingY, canvasColorBar.css.rectWidth, canvasColorBar.css.rectHeight);

            canvasColorBar.ctx.fillStyle = canvasColorBar.css.thirdColor;
            canvasColorBar.ctx.fillRect(canvasColorBar.css.lineWidth / 2 + 2 * canvasColorBar.css.rectWidth, canvasColorBar.css.fontHeight * 16 + canvasColorBar.css.spacingY, canvasColorBar.css.rectWidth, canvasColorBar.css.rectHeight);
            canvasColorBar.ctx.strokeRect(canvasColorBar.css.lineWidth / 2 + 2 * canvasColorBar.css.rectWidth, canvasColorBar.css.fontHeight * 16 + canvasColorBar.css.spacingY, canvasColorBar.css.rectWidth, canvasColorBar.css.rectHeight);

            canvasColorBar.ctx.fillStyle = canvasColorBar.css.founthColor;
            canvasColorBar.ctx.fillRect(canvasColorBar.css.lineWidth / 2 + 3 * canvasColorBar.css.rectWidth, canvasColorBar.css.fontHeight * 16 + canvasColorBar.css.spacingY, canvasColorBar.css.rectWidth, canvasColorBar.css.rectHeight);
            canvasColorBar.ctx.strokeRect(canvasColorBar.css.lineWidth / 2 + 3 * canvasColorBar.css.rectWidth, canvasColorBar.css.fontHeight * 16 + canvasColorBar.css.spacingY, canvasColorBar.css.rectWidth, canvasColorBar.css.rectHeight);

            //        canvasColorBar.ctx.fillStyle = canvasColorBar.css.fifthColor;
            //        canvasColorBar.ctx.fillRect(canvasColorBar.css.lineWidth / 2 + 4 * canvasColorBar.css.rectWidth, canvasColorBar.css.fontHeight * 16 + canvasColorBar.css.spacingY, canvasColorBar.css.rectWidth, canvasColorBar.css.rectHeight);
            //        canvasColorBar.ctx.strokeRect(canvasColorBar.css.lineWidth / 2 + 4 * canvasColorBar.css.rectWidth, canvasColorBar.css.fontHeight * 16 + canvasColorBar.css.spacingY, canvasColorBar.css.rectWidth, canvasColorBar.css.rectHeight);

            //        canvasColorBar.ctx.fillStyle = canvasColorBar.css.sixthColor;
            //        canvasColorBar.ctx.fillRect(canvasColorBar.css.lineWidth / 2 + 5 * canvasColorBar.css.rectWidth, canvasColorBar.css.fontHeight * 16 + canvasColorBar.css.spacingY, canvasColorBar.css.rectWidth, canvasColorBar.css.rectHeight);
            //        canvasColorBar.ctx.strokeRect(canvasColorBar.css.lineWidth / 2 + 5 * canvasColorBar.css.rectWidth, canvasColorBar.css.fontHeight * 16 + canvasColorBar.css.spacingY, canvasColorBar.css.rectWidth, canvasColorBar.css.rectHeight);
            canvasColorBar.ctx.closePath();
            canvasColorBar.ctx.stroke();

        };



        for (var i = 0; i < arr.length; i++) {
            if (arr[i].arcXTemp >= 0 && arr[i].arcYTemp >= 0) {
                canvasMap.ctx.fillStyle = arr[i].arcFillColor;
                canvasMap.ctx.beginPath();
                canvasMap.ctx.arc(arr[i].arcX, arr[i].arcY, (canvasMap.hover == i) ? 1.2 * arr[i].arcRadius * imgScale : arr[i].arcRadius * imgScale, arr[i].arcStartAngle, arr[i].arcEndAngle, true);
                //            canvasMap.ctx.arc(arr[i].arcX, arr[i].arcY, (canvasMap.hover == i) ? 1.2 * arr[i].arcRadius * imgScale : arr[i].arcRadius * imgScale, arr[i].arcStartAngle, arr[i].arcEndAngle, true);
                canvasMap.ctx.fill();
                canvasMap.ctx.stroke();
                canvasMap.ctx.closePath();
                canvasMap.ctx.beginPath();
                canvasMap.ctx.fillStyle = 'rgb(0, 0, 0)';
//                canvasMap.ctx.font=canvasMap.css.font;
                canvasMap.ctx.fillText(arr[i].wellName, arr[i].arcX + canvasMap.css.nameSpacingX * imgScale, arr[i].arcY + canvasMap.css.nameSpacingY * imgScale);
                canvasMap.ctx.closePath();
            };
        };
        if (canvasMap.hover != -1) {
            var i = canvasMap.hover;
            var addRadiusX, addRadiusY, addFloatTipSpacingX, addFloatTipSpacingY, addDivTipOffsetHeight, addDivTipOffsetWidth;
            addRadiusX = arr[i].arcRadius;
            addRadiusY = arr[i].arcRadius;
            addFloatTipSpacingX = tip.css.floatTipSpacingX;
            addFloatTipSpacingY = tip.css.floatTipSpacingY;
            addDivTipOffsetHeight = 0;
            addDivTipOffsetWidth = 0;

            if (arr[i].arcX > canvasMap.ref.offsetWidth / 2) {
                addRadiusX = -addRadiusX; ///////////////?????????
                addFloatTipSpacingX = -addFloatTipSpacingX;
                addDivTipOffsetWidth = -tip.ref.offsetWidth;


            };
            if (arr[i].arcY > canvasMap.ref.offsetHeight / 2) {
                addRadiusY = -addRadiusY;
                addFloatTipSpacingY = -addFloatTipSpacingY;
                addDivTipOffsetHeight = -tip.ref.offsetHeight;
            };

            //            tip.ref.style.top = container.ref.offsetTop + arr[i].arcY + addRadiusY * imgScale + addFloatTipSpacingY + addDivTipOffsetHeight + "px";
            //            tip.ref.style.left = container.ref.offsetLeft + arr[i].arcX + addRadiusX * imgScale + addFloatTipSpacingX + addDivTipOffsetWidth + "px";

            tip.ref.style.top = arr[i].arcY + addRadiusY * imgScale + addFloatTipSpacingY + addDivTipOffsetHeight + "px";
            tip.ref.style.left = arr[i].arcX + addRadiusX * imgScale + addFloatTipSpacingX + addDivTipOffsetWidth + "px";
            
            
            $(subTip.ref).html("");
            $(subTip.ref).html("<ul id=\"global_wellMap_subDivTipID_ul\"></ul>");          

            if (arr[i].dataList != undefined) {
               
                for (var j = 0; j < arr[i].dataList.length; j++) {                    
                    $("#global_wellMap_subDivTipID_ul").html( $("#global_wellMap_subDivTipID_ul").html()+"<li>"+arr[i].dataList[j].key + "&nbsp:&nbsp" + arr[i].dataList[j].value+"</li>");                                  
                }
               
            }
            if (dataType == 1) {
                tip.ref.style.visibility = 'visible';
            } else {
                tip.ref.style.visibility = 'hidden';
            }

            canvasMap.hover = -1;
        } else {

            tip.ref.style.visibility = 'hidden';
        };
    }

};
////////////////////////////////////////////////////////////////////////////////////////////////////////////
global.wellMap.canvasMap.event.mouseClick = function (event) {
    if (event.ctrlKey == true && global.wellMap.dataType == 2) {
        var pos1 = windowToCanvas(global.wellMap.canvasMap.ref, event.clientX, event.clientY);
        global.wellMap.arr[global.wellMap.flag.locateWellMark].arcX = pos1.x;
        global.wellMap.arr[global.wellMap.flag.locateWellMark].arcY = pos1.y;
        global.wellMap.saveWell();
        global.wellMap.drawImage();
    };
    global.wellMap.canvasMap.ref.style.cursor = "default";
    global.wellMap.canvasMap.ref.onmousedown = null;
    global.wellMap.canvasMap.ref.onmouseup = null;
    global.wellMap.canvasMap.ref.onmousemove = null;
    global.wellMap.canvasMap.ref.onclick = null;
    //
    //    window.onmousedown = null;
    //    window.onmouseup = null;
    //    window.onclick = null;

}
global.wellMap.canvasMap.event.mouseDown = function (event) {
    var pos = windowToCanvas(global.wellMap.canvasMap.ref, event.clientX, event.clientY);

    if (global.wellMap.flag.add == true) {
        global.wellMap.arr.push({
            wellName: 'wellName',
            arcFillColor: 'rgb(0,228,0)',
            arcX: pos.x,
            arcY: pos.y,
            arcXTemp: 0,
            arcYTemp: 0,
            imgScale: 1,
            arcRadius: 10, //半径
            arcStartAngle: 0, //起始角度        
            arcEndAngle: 2 * Math.PI, //终止角度
            dataList: [{
                key: "",
                value: ""
}]
        });
    } else if (global.wellMap.flag.delete == true) {
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        for (var i = 0; i < global.wellMap.arr.length; i++) {
            if (Math.abs(pos.x - global.wellMap.arr[i].arcX) < global.wellMap.arr[i].arcRadius && Math.abs(pos.y - global.wellMap.arr[i].arcY) < global.wellMap.arr[i].arcRadius) {
                global.wellMap.flag.locateWellMark = i;
                global.wellMap.arr.splice(global.wellMap.flag.locateWellMark, 1);
            };

        };
    } else if (global.wellMap.flag.drag == true) {
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        //        for (var i = 0; i < global.wellMap.arr.length; i++) {
        //
        //            if (Math.abs(pos.x - global.wellMap.arr[i].arcX) < global.wellMap.arr[i].arcRadius && Math.abs(pos.y - global.wellMap.arr[i].arcY) < global.wellMap.arr[i].arcRadius) {
        //                global.wellMap.flag.locateWellMark = i;
        ////////////////////////////////////////////////////////////////////////////////////////////////////////

        window.onmousemove = function (event) {
            for (var i = 0; i < global.wellMap.arr.length; i++) {

                if (Math.abs(pos.x - global.wellMap.arr[i].arcX) < global.wellMap.arr[i].arcRadius && Math.abs(pos.y - global.wellMap.arr[i].arcY) < global.wellMap.arr[i].arcRadius) {
                    global.wellMap.flag.locateWellMark = i;

                    global.wellMap.canvasMap.ref.style.cursor = "pointer";
                    ///////////////////////////////////////////////////////////////////////////////////////////////
                    var pos1 = windowToCanvas(global.wellMap.canvasMap.ref, event.clientX, event.clientY);

                    var x = pos1.x - pos.x;

                    var y = pos1.y - pos.y;

                    global.wellMap.arr[global.wellMap.flag.locateWellMark].arcX += x;
                    global.wellMap.arr[global.wellMap.flag.locateWellMark].arcY += y;
                    pos = pos1;
                    global.wellMap.flag.dragWellStatus = true;
                    global.wellMap.drawImage();
                    break;

                }
            };
            if (global.wellMap.flag.dragWellStatus == false) {
                global.wellMap.canvasMap.ref.style.cursor = "pointer";

                var pos1 = windowToCanvas(global.wellMap.canvasMap.ref, event.clientX, event.clientY);

                var x = pos1.x - pos.x;

                var y = pos1.y - pos.y;

                pos = pos1;

                global.wellMap.imgX += x;

                global.wellMap.imgY += y;
                for (var i = 0; i < global.wellMap.arr.length; i++) {
                    global.wellMap.arr[i].arcX += x;
                    global.wellMap.arr[i].arcY += y;
                }

                global.wellMap.drawImage();

            }

        };
        window.onmouseup = function (event) {
            window.onmouseup = null;
            window.onmousemove = null;
        }

    } else if (global.wellMap.flag.add == false && global.wellMap.flag.delete == false && global.wellMap.flag.drag == false) {
        ////////////////////////////////////////////////////////////////////////////////////////////////////////

        //        global.wellMap.canvasMap.ref.onmousemove = function (event) {

        window.onmousemove = function (event) {

            global.wellMap.canvasMap.ref.style.cursor = "pointer";

            var pos1 = windowToCanvas(global.wellMap.canvasMap.ref, event.clientX, event.clientY);

            var x = pos1.x - pos.x;

            var y = pos1.y - pos.y;

            pos = pos1;

            global.wellMap.imgX += x;

            global.wellMap.imgY += y;
            for (var i = 0; i < global.wellMap.arr.length; i++) {
                global.wellMap.arr[i].arcX += x;
                global.wellMap.arr[i].arcY += y;
            }

            global.wellMap.drawImage();

        };
        window.onmouseup = function (event) {
            window.onmouseup = null;
            window.onmousemove = null;
        }

    }


};

global.wellMap.canvasMap.event.wheel = function (event) {

    var pos = windowToCanvas(global.wellMap.canvasMap.ref, event.clientX, event.clientY);

    event.wheelDelta = event.wheelDelta ? event.wheelDelta : (event.deltaY * (-40));

    if (event.wheelDelta > 0) {
        with(global.wellMap) {
            imgScale *= 2;

            imgX = imgX * 2 - pos.x;

            imgY = imgY * 2 - pos.y;
            for (i = 0; i < arr.length; i++) {
                arr[i].arcX = arr[i].arcX * 2 - pos.x;
                arr[i].arcY = arr[i].arcY * 2 - pos.y;
            };
        };

    } else {
        with(global.wellMap) {
            imgScale /= 2;

            imgX = imgX * 0.5 + pos.x * 0.5;

            imgY = imgY * 0.5 + pos.y * 0.5;
            for (i = 0; i < arr.length; i++) {
                arr[i].arcX = arr[i].arcX * 0.5 + pos.x * 0.5;
                arr[i].arcY = arr[i].arcY * 0.5 + pos.y * 0.5;
            };
        };

    }
    //    with(global.wellMap) {
    //        if (!(img.width * imgScale > canvasMap.ref.clientWidth)) { //如果图像宽度小于等于画布宽度，图像X方向绘制在中间位置
    //            imgXTemp = imgX;
    //            imgX = (canvasMap.ref.clientWidth - img.width * imgScale) / 2;
    //            for (var i = 0; i < arr.length; i++) {
    //                arr[i].arcX = arr[i].arcX + imgX - imgXTemp;
    //
    //            };
    //
    //        };
    //        if (!(img.height * imgScale > canvasMap.ref.clientHeight)) { //如果图像高度小于等于画布高度，图像Y方向绘制在中间位置
    //            imgYTemp = imgY;
    //            imgY = (canvasMap.ref.clientHeight - img.height * imgScale) / 2;
    //            for (var i = 0; i < arr.length; i++) {
    //                arr[i].arcY = arr[i].arcY + imgY - imgYTemp;
    //            };
    //        };
    //
    //        if (img.width * imgScale > canvasMap.ref.clientWidth) { //如果图像宽度大于画布宽度
    //            if (imgX > 0) { //拖动图像超过左边界，图像弹回左边界
    //                imgXTemp = imgX;
    //                imgX = 0;
    //                for (var i = 0; i < arr.length; i++) {
    //                    arr[i].arcX = arr[i].arcX + imgX - imgXTemp;
    //
    //                };
    //            };
    //            if (imgX + img.width * imgScale < canvasMap.ref.clientWidth) { //拖动图像超过右边界，图像弹回右边界
    //                imgXTemp = imgX;
    //                imgX = canvasMap.ref.clientWidth - img.width * imgScale;
    //                for (var i = 0; i < arr.length; i++) {
    //                    arr[i].arcX = arr[i].arcX + imgX - imgXTemp;
    //
    //                };
    //            };
    //        };
    //        if (img.height * imgScale > canvasMap.ref.clientHeight) { //如果图像高度大于画布高度
    //            if (imgY > 0) { //拖动图像超过上边界，图像弹上左边界
    //                imgYTemp = imgY;
    //                imgY = 0;
    //                for (var i = 0; i < arr.length; i++) {
    //                    arr[i].arcY = arr[i].arcY + imgY - imgYTemp;
    //                };
    //            };
    //            if (imgY + img.height * imgScale < canvasMap.ref.clientHeight) { //拖动图像超过下边界，图像弹回下边界
    //                imgYTemp = imgY;
    //                imgY = canvasMap.ref.clientHeight - img.height * imgScale;
    //                for (var i = 0; i < arr.length; i++) {
    //                    arr[i].arcY = arr[i].arcY + imgY - imgYTemp;
    //                };
    //            };
    //
    //        };
    //    };
    global.wellMap.drawImage();

};

global.wellMap.canvasMap.event.mouseUp = function (event) {
    //    with(global.wellMap) {
    //        if (!(img.width * imgScale > canvasMap.ref.clientWidth)) { //如果图像宽度小于等于画布宽度，图像X方向绘制在中间位置
    //            imgXTemp = imgX;
    //            imgX = (canvasMap.ref.clientWidth - img.width * imgScale) / 2;
    //            for (var i = 0; i < arr.length; i++) {
    //                arr[i].arcX = arr[i].arcX + imgX - imgXTemp;
    //            };
    //        };
    //        if (!(img.height * imgScale > canvasMap.ref.clientHeight)) { //如果图像高度小于等于画布高度，图像Y方向绘制在中间位置
    //            imgYTemp = imgY;
    //            imgY = (canvasMap.ref.clientHeight - img.height * imgScale) / 2;
    //            for (var i = 0; i < arr.length; i++) {
    //                arr[i].arcY = arr[i].arcY + imgY - imgYTemp;
    //            };
    //        };
    //
    //        if (img.width * imgScale > canvasMap.ref.clientWidth) { //如果图像宽度大于画布宽度
    //            if (imgX > 0) { //拖动图像超过左边界，图像弹回左边界
    //                imgXTemp = imgX;
    //                imgX = 0;
    //                for (var i = 0; i < arr.length; i++) {
    //                    arr[i].arcX = arr[i].arcX + imgX - imgXTemp;
    //                };
    //            };
    //            if (imgX + img.width * imgScale < canvasMap.ref.clientWidth) { //拖动图像超过右边界，图像弹回右边界
    //                imgXTemp = imgX;
    //                imgX = canvasMap.ref.clientWidth - img.width * imgScale;
    //                for (var i = 0; i < arr.length; i++) {
    //                    arr[i].arcX = arr[i].arcX + imgX - imgXTemp;
    //                };
    //            };
    //        };
    //        if (img.height * imgScale > canvasMap.ref.clientHeight) { //如果图像高度大于画布高度
    //            if (imgY > 0) { //拖动图像超过上边界，图像弹上左边界
    //                imgYTemp = imgY;
    //                imgY = 0;
    //                for (var i = 0; i < arr.length; i++) {
    //                    arr[i].arcY = arr[i].arcY + imgY - imgYTemp;
    //                };
    //            };
    //            if (imgY + img.height * imgScale < canvasMap.ref.clientHeight) { //拖动图像超过下边界，图像弹回下边界
    //                imgYTemp = imgY;
    //                imgY = canvasMap.ref.clientHeight - img.height * imgScale;
    //                for (var i = 0; i < arr.length; i++) {
    //                    arr[i].arcY = arr[i].arcY + imgY - imgYTemp;
    //                };
    //            };
    //
    //        };
    //    };



    //    global.wellMap.drawImage();
    if ((event.ctrlKey == true && global.wellMap.dataType == 2) || global.wellMap.flag.dragWellStatus == true) {
        global.wellMap.saveWell();

    }
    //    
    global.wellMap.canvasMap.ref.style.cursor = "default";
    global.wellMap.flag.dragWellStatus = false;
    global.wellMap.canvasMap.ref.onmouseup = null;
    global.wellMap.canvasMap.ref.onmousemove = null;

};


global.wellMap.canvasMap.event.mouseMove = function (event) {

    var pos = windowToCanvas(global.wellMap.canvasMap.ref, event.clientX, event.clientY);
    for (var i = 0; i < global.wellMap.arr.length; i++) { // 检查每一个圆，看鼠标是否滑过

        if (Math.pow(pos.x - global.wellMap.arr[i].arcX, 2) + Math.pow(pos.y - global.wellMap.arr[i].arcY, 2) < Math.pow(global.wellMap.imgScale * global.wellMap.arr[i].arcRadius, 2)) {

            global.wellMap.canvasMap.hover = i;
            break;
        };

    };

    global.wellMap.drawImage();
};
global.wellMap.canvasIcon.event.mouseMove = function (event) {

    var pos = windowToCanvas(global.wellMap.canvasIcon.ref, event.clientX, event.clientY);


    if (Math.pow(pos.x - global.wellMap.canvasIcon.css.arcAddX, 2) + Math.pow(pos.y - global.wellMap.canvasIcon.css.arcAddY, 2) < Math.pow(global.wellMap.canvasIcon.css.radius, 2)) {

        global.wellMap.canvasIcon.hover = 1;
    } else if (Math.pow(pos.x - global.wellMap.canvasIcon.css.arcMinusX, 2) + Math.pow(pos.y - global.wellMap.canvasIcon.css.arcMinusY, 2) < Math.pow(global.wellMap.canvasIcon.css.radius, 2)) {

        global.wellMap.canvasIcon.hover = 2;
    } else {
        global.wellMap.canvasIcon.hover = -1;
    };

    global.wellMap.drawImage();
};
global.wellMap.canvasIcon.event.mouseDown = function (event) {

    if (global.wellMap.canvasIcon.hover == 1) {
        with(global.wellMap) {

            imgScale *= 2;
            imgX = imgX * 2 - canvasMap.ref.clientWidth / 2;
            imgY = imgY * 2 - canvasMap.ref.clientHeight / 2;

            for (i = 0; i < arr.length; i++) {
                arr[i].arcX = arr[i].arcX * 2 - canvasMap.ref.clientWidth / 2;
                arr[i].arcY = arr[i].arcY * 2 - canvasMap.ref.clientHeight / 2;
            };
        };

    } else if (global.wellMap.canvasIcon.hover == 2) {
        with(global.wellMap) {

            imgScale /= 2;
            imgX = (imgX + canvasMap.ref.clientWidth / 2) * 0.5;
            imgY = (imgY + canvasMap.ref.clientHeight / 2) * 0.5;

            for (i = 0; i < arr.length; i++) {
                arr[i].arcX = (arr[i].arcX + canvasMap.ref.clientWidth / 2) * 0.5;
                arr[i].arcY = (arr[i].arcY + canvasMap.ref.clientHeight / 2) * 0.5;
            };
        };

    }

    //    var pos = windowToCanvas(global.wellMap.canvasIcon.ref, event.clientX, event.clientY);
    //    if (global.wellMap.canvasIcon.hover == 1) {
    //        with(global.wellMap) {
    //            imgScale *= 2;
    //
    //            imgX = imgX * 2 - pos.x;
    //
    //            imgY = imgY * 2 - pos.y;
    //            for (i = 0; i < arr.length; i++) {
    //                arr[i].arcX = arr[i].arcX * 2 - pos.x;
    //                arr[i].arcY = arr[i].arcY * 2 - pos.y;
    //            };
    //        };
    //
    //    } else if (global.wellMap.canvasIcon.hover == 2) {
    //        with(global.wellMap) {
    //            imgScale /= 2;
    //
    //            imgX = imgX * 0.5 + pos.x * 0.5;
    //            imgY = imgY * 0.5 + pos.y * 0.5;
    //            for (i = 0; i < arr.length; i++) {
    //                arr[i].arcX = arr[i].arcX * 0.5 + pos.x * 0.5;
    //                arr[i].arcY = arr[i].arcY * 0.5 + pos.y * 0.5;
    //            };
    //        };
    //
    //    }
    //    with(global.wellMap) {
    //        if (!(img.width * imgScale > canvasMap.ref.clientWidth)) { //如果图像宽度小于等于画布宽度，图像X方向绘制在中间位置
    //            imgXTemp = imgX;
    //            imgX = (canvasMap.ref.clientWidth - img.width * imgScale) / 2;
    //            for (var i = 0; i < arr.length; i++) {
    //                arr[i].arcX = arr[i].arcX + imgX - imgXTemp;
    //
    //            };
    //
    //        };
    //        if (!(img.height * imgScale > canvasMap.ref.clientHeight)) { //如果图像高度小于等于画布高度，图像Y方向绘制在中间位置
    //            imgYTemp = imgY;
    //            imgY = (canvasMap.ref.clientHeight - img.height * imgScale) / 2;
    //            for (var i = 0; i < arr.length; i++) {
    //                arr[i].arcY = arr[i].arcY + imgY - imgYTemp;
    //            };
    //        };
    //
    //        if (img.width * imgScale > canvasMap.ref.clientWidth) { //如果图像宽度大于画布宽度
    //            if (imgX > 0) { //拖动图像超过左边界，图像弹回左边界
    //                imgXTemp = imgX;
    //                imgX = 0;
    //                for (var i = 0; i < arr.length; i++) {
    //                    arr[i].arcX = arr[i].arcX + imgX - imgXTemp;
    //
    //                };
    //            };
    //            if (imgX + img.width * imgScale < canvasMap.ref.clientWidth) { //拖动图像超过右边界，图像弹回右边界
    //                imgXTemp = imgX;
    //                imgX = canvasMap.ref.clientWidth - img.width * imgScale;
    //                for (var i = 0; i < arr.length; i++) {
    //                    arr[i].arcX = arr[i].arcX + imgX - imgXTemp;
    //
    //                };
    //            };
    //        };
    //        if (img.height * imgScale > canvasMap.ref.clientHeight) { //如果图像高度大于画布高度
    //            if (imgY > 0) { //拖动图像超过上边界，图像弹上左边界
    //                imgYTemp = imgY;
    //                imgY = 0;
    //                for (var i = 0; i < arr.length; i++) {
    //                    arr[i].arcY = arr[i].arcY + imgY - imgYTemp;
    //                };
    //            };
    //            if (imgY + img.height * imgScale < canvasMap.ref.clientHeight) { //拖动图像超过下边界，图像弹回下边界
    //                imgYTemp = imgY;
    //                imgY = canvasMap.ref.clientHeight - img.height * imgScale;
    //                for (var i = 0; i < arr.length; i++) {
    //                    arr[i].arcY = arr[i].arcY + imgY - imgYTemp;
    //                };
    //            };
    //
    //        };
    //    };

    global.wellMap.drawImage();
};

function windowToCanvas(canvas, x, y) {

    var bbox = canvas.getBoundingClientRect();

    return {

        x: x - bbox.left - (bbox.width - canvas.width) / 2,

        y: y - bbox.top - (bbox.height - canvas.height) / 2

    };

};