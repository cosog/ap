//采控单元
var protocolAcqUnitConfigItemsHandsontableHelper = null;
var protocolConfigAcqUnitPropertiesHandsontableHelper = null;

var _currentAcqProtocolNode = null;
var _currentAcqUnitNode=null;
	 // 单元列表树选中状态
var _selectedAcqUnitId = null;
var _selectedAcqUnitClasses = null;

// 新增对象后需要高亮的节点信息（由添加函数设置）
var _newAcqUnitObjectName = null;
var _newAcqUnitObjectClasses = null;

//================================================================
// 采控单元 - 协议树事件
// ================================================================
function onAcqProtocolTreeBeforeLoad(e) {
    var params = e.params || {};
    // 从设备类型树获取过滤条件（与主协议树一致）
    if (selectedDeviceTypeId) {
        params.deviceTypeIds = selectedDeviceTypeId;
    }
    e.params = params;
}

function onAcqProtocolTreeLoad(e) {
	    var tree = e.sender;
	    var root = tree.getRootNode();
	    if (!root) return;

	    var targetNode = null;
	    // 如果有记录的上次选中的协议 code，优先选中它
	    if (_selectedUnitConfigProtocolTreeNodeCode) {
	        // 深度遍历查找
	        function findNode(node) {
	            if (node.code === _selectedUnitConfigProtocolTreeNodeCode && node.classes === 1) {
	                targetNode = node;
	                return true;
	            }
	            if (node.children) {
	                for (var i = 0; i < node.children.length; i++) {
	                    if (findNode(node.children[i])) return true;
	                }
	            }
	            return false;
	        }
	        findNode(root);
	    }

	    if (!targetNode) {
	        // 否则选第一个协议
	        var protocolNodes = [];
	        function collect(node) {
	            if (node.children && node.children.length > 0) {
	                for (var i = 0; i < node.children.length; i++) collect(node.children[i]);
	            } else {
	                if (node.classes == 1) protocolNodes.push(node);
	            }
	        }
	        collect(root);
	        if (protocolNodes.length > 0) targetNode = protocolNodes[0];
	    }

	    if (targetNode) {
	    	setTimeout(function() {
	    		tree.selectNode(targetNode);
           }, 50);
	    }
	}

function onAcqProtocolTreeSelect(e) {
    var node = e.node;
    _currentAcqProtocolNode = node;
    if (node && node.classes === 1) {
    	_selectedUnitConfigProtocolTreeNodeCode = node.code;
    	
    	_newAcqUnitObjectName = null;
        _newAcqUnitObjectClasses = null;
    }
    loadAcqUnitList(node);
}

function loadAcqUnitList(protocolNode) {
    var tree = mini.get('acqUnitListTree');
    if (!tree) return;
    if (!tree.getUrl()) {
        tree.setUrl(context + '/acquisitionUnitManagerController/acquisitionUnitTreeData');  // 需后端提供
    }
    tree.load();
}

function onAcqUnitListBeforeLoad(e) {
	    var params = e.params || {};
	    if (_currentAcqProtocolNode) {
	    	if(_currentAcqProtocolNode.classes==1){
	    		params.protocol = _currentAcqProtocolNode.code;
	    	}else{
	    		var protocolList=[];
	    		if(isNotVal(_currentAcqProtocolNode.children)){
  				for(var i=0;i<_currentAcqProtocolNode.children.length;i++){
  					protocolList.push(_currentAcqProtocolNode.children[i].code);
  				}
  			}
	    		params.protocol=protocolList.join(",");
	    	}
	    	
	    }
	    e.params = params;
	}

function onAcqUnitListLoad(e) {
	    var tree = e.sender;
	    var root = tree.getRootNode();
	    if (!root) return;

	    var targetNode = null;

	    // 1. 处理新增对象高亮（优先）
	    if (_newAcqUnitObjectName && _newAcqUnitObjectClasses !== null) {
	        function findNewNode(node) {
	            if (node.text === _newAcqUnitObjectName && node.classes === _newAcqUnitObjectClasses) {
	                targetNode = node;
	                return true;
	            }
	            if (node.children) {
	                for (var i = 0; i < node.children.length; i++) {
	                    if (findNewNode(node.children[i])) return true;
	                }
	            }
	            return false;
	        }
	        findNewNode(root);
	        // 找到后清除标记，避免重复高亮
	        if (targetNode) {
	            _newAcqUnitObjectName = null;
	            _newAcqUnitObjectClasses = null;
	        }
	    }

	    // 2. 如果没有新增高亮，按记录的 ID 恢复
	    if (!targetNode && _selectedAcqUnitId && _selectedAcqUnitClasses) {
	    	function findNewNode(node) {
	            if (node.id === _selectedAcqUnitId && node.classes === _selectedAcqUnitClasses) {
	                targetNode = node;
	                return true;
	            }
	            if (node.children) {
	                for (var i = 0; i < node.children.length; i++) {
	                    if (findNewNode(node.children[i])) return true;
	                }
	            }
	            return false;
	        }
	        findNewNode(root);
	    }

	    // 3. 若都没有，选第一个采集单元或组（修正后只取第一个）
	    if (!targetNode) {
	        function collect(node) {
	            if (targetNode) return; // 已找到则停止
	            if (node.children && node.children.length > 0) {
	                for (var i = 0; i < node.children.length; i++) {
	                    collect(node.children[i]);
	                    if (targetNode) return; // 找到后立即中断循环
	                }
	            } else {
	                if (node.classes === 2 || node.classes === 3) {
	                    targetNode = node;
	                }
	            }
	        }
	        collect(root);
	    }

	    setTimeout(function () {
	        if (targetNode) {
	            tree.selectNode(targetNode);
	        } else {
	            // 如果仍无目标，选根节点的第一个子节点（通常是协议节点）
	            if (root.children && root.children.length > 0) {
	                tree.selectNode(root.children[0]);
	            }
	        }
	    }, 50);
	}

//采控单元列表选中事件
function onAcqUnitListSelect(e) {
  var node = e.node;
  if (!node) return;
  _currentAcqUnitNode = node;
  _selectedAcqUnitId = node.id;
  _selectedAcqUnitClasses = node.classes;

  var tabs = mini.get('acqUnitDetailTabs');
  if (!tabs) return;

  var propsTab = tabs.getTab('props');
  var configTab = tabs.getTab('config');
  if (!propsTab || !configTab) return;

  var classes = node.classes;

  // 根/协议节点：隐藏所有
  if (classes === 0 || classes === 1) {
      tabs.updateTab(propsTab, { visible: false });
      tabs.updateTab(configTab, { visible: false });
      //destroyAcqUnitHelpers();
      return;
  }

  if (classes === 2) {
      tabs.updateTab(propsTab, { visible: true });
      tabs.updateTab(configTab, { visible: false });
      // 如果当前激活不是属性 Tab，则激活它；但无论如何，节点变了，需要加载属性数据
      var active = tabs.getActiveTab();
      if (!active || active.name !== 'props') {
          tabs.activeTab('props');
      }else{
      	loadAcqUnitProperties(node);
      }
  } else if (classes === 3) {
      tabs.updateTab(propsTab, { visible: true });
      tabs.updateTab(configTab, { visible: true });
      // 默认激活配置 Tab
      var active = tabs.getActiveTab();
      if (!active) {
          tabs.activeTab('config');
      }else{
      	var active = tabs.getActiveTab();
      	if(active.name == 'props'){
      		loadAcqUnitProperties(node);
      	}else{
          	loadAcqUnitConfig(node);
      	}
      }
  }
}

//Tab 切换事件
function onAcqUnitDetailTabChanged(e) {
    if (!_currentAcqUnitNode) return;
    var tabs = e.sender;
    var active = tabs.getActiveTab();
    if (!active) return;
    var tabName = active.name; // 'props' 或 'config'
    if (tabName === 'props') {
        loadAcqUnitProperties(_currentAcqUnitNode);
    } else if (tabName === 'config') {
        loadAcqUnitConfig(_currentAcqUnitNode);
    }
}

function loadAcqUnitProperties(node) {
	    var container = document.getElementById('acqUnitPropertiesContainer');
	    if (!container) return;

	    // 销毁已有表格
	    if (protocolConfigAcqUnitPropertiesHandsontableHelper) {
	        if (protocolConfigAcqUnitPropertiesHandsontableHelper.hot) {
	            protocolConfigAcqUnitPropertiesHandsontableHelper.hot.destroy();
	        }
	        protocolConfigAcqUnitPropertiesHandsontableHelper = null;
	    }

	    // 构建表格数据（root）
	    var root = [];
	    var classes = node.classes;

	    if (classes === 0) {
	        root.push({ id: 1, title: _loginUserLanguageResource.rootNode, value: _loginUserLanguageResource.unitList });
	    } else if (classes === 1) {
	        root.push({ id: 1, title: _loginUserLanguageResource.protocolName, value: node.text });
	    } else if (classes === 2) {
	        root.push({ id: 1, title: _loginUserLanguageResource.unitName, value: node.text });
	        root.push({ id: 2, title: _loginUserLanguageResource.sequenceNumber, value: node.sort });
	        root.push({ id: 3, title: _loginUserLanguageResource.remark, value: node.remark });
	    } else if (classes === 3) {
	        root.push({ id: 1, title: _loginUserLanguageResource.groupName, value: node.text });
	        root.push({ id: 2, title: _loginUserLanguageResource.groupType, value: node.typeName });
	        if (node.type === 0) { // 采集组
	            root.push({ id: 3, title: _loginUserLanguageResource.groupTimingInterval + '(s)', value: node.groupTimingInterval });
	            root.push({ id: 4, title: _loginUserLanguageResource.groupSavingInterval + '(s)', value: node.groupSavingInterval });
	            root.push({ id: 5, title: _loginUserLanguageResource.remark, value: node.remark });
	        } else if (node.type === 1) { // 控制组
	            root.push({ id: 3, title: _loginUserLanguageResource.remark, value: node.remark });
	        }
	    }
	    // 创建 Handsontable Helper
	    protocolConfigAcqUnitPropertiesHandsontableHelper = ProtocolConfigAcqUnitPropertiesHandsontableHelper.createNew('acqUnitPropertiesContainer');
	    // 设置列头
	    var colHeaders = [_loginUserLanguageResource.idx, _loginUserLanguageResource.variable, _loginUserLanguageResource.value];
	    var columns = [{ data: 'id' }, { data: 'title' }, { data: 'value' }];
	    protocolConfigAcqUnitPropertiesHandsontableHelper.colHeaders = colHeaders;
	    protocolConfigAcqUnitPropertiesHandsontableHelper.columns = columns;
	    protocolConfigAcqUnitPropertiesHandsontableHelper.classes = classes;
	    protocolConfigAcqUnitPropertiesHandsontableHelper.type = node.type;
	    protocolConfigAcqUnitPropertiesHandsontableHelper.createTable(root);
	}

//加载配置数据
function loadAcqUnitConfig(node) {
  var container = document.getElementById('acqUnitConfigContainer');
  if (!container) return;

  if (protocolAcqUnitConfigItemsHandsontableHelper) {
      if (protocolAcqUnitConfigItemsHandsontableHelper.hot) {
          protocolAcqUnitConfigItemsHandsontableHelper.hot.destroy();
      }
      protocolAcqUnitConfigItemsHandsontableHelper = null;
  }

  var protocolCode = node.protocol || '';
  var classes = node.classes || 0;
  var code = node.code || '';
  var type = node.type || 0;
  var text = node.text || '';
	var divId='acqUnitDetailTabs';
  var mask = mini.mask({
      el: divId,
      cls: 'mini-mask-loading',
      html: _loginUserLanguageResource.loadingData
  });

  $.ajax({
      type: 'POST',
      url: context + '/acquisitionUnitManagerController/getProtocolAcqUnitItemsConfigData',
      data: {
          protocolCode: protocolCode,
          classes: classes,
          code: code,
          type: type
      },
      dataType: 'json',
      success: function (result) {
      	mini.unmask(divId);
          if (!result.success) {
              mini.alert(result.message || _loginUserLanguageResource.requestFailed);
              return;
          }
          var tableData = result.totalRoot || [];
          if (tableData.length === 0) {
              for (var i = 0; i < 30; i++) tableData.push({});
          }

          protocolAcqUnitConfigItemsHandsontableHelper = ProtocolAcqUnitConfigItemsHandsontableHelper.createNew('acqUnitConfigContainer');

          var colHeaders = ['', _loginUserLanguageResource.idx, _loginUserLanguageResource.name, _loginUserLanguageResource.startAddress, _loginUserLanguageResource.RWType, _loginUserLanguageResource.unit, _loginUserLanguageResource.resolutionMode, '', _loginUserLanguageResource.dailyCalculate, _loginUserLanguageResource.dailyCalculateColumn];
          var columns = [
              { data: 'checked', type: 'checkbox' },
              { data: 'id' },
              { data: 'showTitle' },
              { data: 'addr' },
              { data: 'RWType' },
              { data: 'unit' },
              { data: 'resolutionMode' },
              { data: 'bitIndex' },
              { data: 'dailyTotalCalculate', type: 'checkbox' },
              { data: 'dailyTotalCalculateName' },
              { data: 'title' },
              { data: 'highLowByte' }
          ];

          protocolAcqUnitConfigItemsHandsontableHelper.colHeaders = colHeaders;
          protocolAcqUnitConfigItemsHandsontableHelper.columns = columns;

          // 根据 classes 和 type 设置隐藏列和列宽
          if (classes === 3 && type === 0) {
              protocolAcqUnitConfigItemsHandsontableHelper.hiddenColumns = [3, 4, 5, 6, 7, 10, 11];
              protocolAcqUnitConfigItemsHandsontableHelper.colWidths = [25, 25, 140, 60, 80, 80, 80, 80, 80, 80];
          } else if (classes === 3 && type === 1) {
              protocolAcqUnitConfigItemsHandsontableHelper.hiddenColumns = [3, 4, 5, 6, 7, 8, 9, 10, 11];
              protocolAcqUnitConfigItemsHandsontableHelper.colWidths = [20, 20, 200, 60, 80, 80, 80, 80, 80, 80];
          } else {
              protocolAcqUnitConfigItemsHandsontableHelper.hiddenColumns = [0, 3, 4, 5, 6, 7, 8, 9, 10, 11];
              protocolAcqUnitConfigItemsHandsontableHelper.colWidths = [20, 20, 240, 60, 80, 80, 80, 80, 80, 80];
          }

          protocolAcqUnitConfigItemsHandsontableHelper.createTable(tableData);
      },
      error: function () {
      	mini.unmask(divId);
          mini.alert(_loginUserLanguageResource.requestFailed);
      }
  });
}

function acqUnitConfigSelectAll() {
    if (protocolAcqUnitConfigItemsHandsontableHelper && protocolAcqUnitConfigItemsHandsontableHelper.hot) {
        var rowCount = protocolAcqUnitConfigItemsHandsontableHelper.hot.countRows();
        var updateData = [];
        for (var i = 0; i < rowCount; i++) {
            updateData.push([i, 'checked', true]);
        }
        protocolAcqUnitConfigItemsHandsontableHelper.hot.setDataAtRowProp(updateData);
    }
}

function acqUnitConfigDeselectAll() {
    if (protocolAcqUnitConfigItemsHandsontableHelper && protocolAcqUnitConfigItemsHandsontableHelper.hot) {
        var rowCount = protocolAcqUnitConfigItemsHandsontableHelper.hot.countRows();
        var updateData = [];
        for (var i = 0; i < rowCount; i++) {
            updateData.push([i, 'checked', false]);
        }
        protocolAcqUnitConfigItemsHandsontableHelper.hot.setDataAtRowProp(updateData);
    }
}

function SaveModbusProtocolAcqUnitConfigTreeData() {
    var tree = mini.get('acqUnitListTree');
    if (!tree) {
        return;
    }
    var selectedNode = tree.getSelectedNode();
    if (!selectedNode) {
        return;
    }

    var classes = selectedNode.classes;
    var tabs = mini.get('acqUnitDetailTabs');
    if (!tabs) {
        return;
    }
    var activeTab = tabs.getActiveTab();
    if (!activeTab) {
        return;
    }
    var activeName = activeTab.name; // 'props' 或 'config'

    if (classes === 2) {
        // 采集单元：只能保存属性（配置Tab被隐藏）
        if (activeName !== 'props') {
            return;
        }
        saveAcqUnitProperties(selectedNode);
    } else if (classes === 3) {
        // 采集组：根据当前激活的Tab决定保存属性还是配置
        if (activeName === 'props') {
            saveAcqGroupProperties(selectedNode);
        } else if (activeName === 'config') {
            grantAcquisitionItemsPermission(selectedNode);
        }
    } else {
    	
    }
}

function saveAcqUnitProperties(node) {
    var helper = protocolConfigAcqUnitPropertiesHandsontableHelper;
    if (!helper || !helper.hot) {
        return;
    }
    var data = helper.hot.getData();
    // data 是数组，每行三个元素 [id, title, value]
    var unitName = isNotVal(data[0][2])?data[0][2]:"";
    var sort = isNotVal(data[1][2])?data[1][2]:"";
    var remark = isNotVal(data[2][2])?data[2][2]:"";
    
    var acqUnitSaveData = {
        updatelist: [{
            classes: node.classes,
            id: node.id,
            unitCode: node.code,
            unitName: unitName,
            sort: sort,
            remark: remark
        }]
    };

    var protocol = node.protocol || '';
    var deviceType = node.deviceType || 0;
    // 获取父节点（协议节点）的 deviceType
    var parentNode = node.parentNode;
    if (parentNode && parentNode.deviceType !== undefined) {
        deviceType = parentNode.deviceType;
    }

    saveAcquisitionUnitConfigData(acqUnitSaveData, protocol, deviceType);
}

function saveAcqGroupProperties(node) {
    var helper = protocolConfigAcqUnitPropertiesHandsontableHelper;
    if (!helper || !helper.hot) {
        return;
    }
    var data = helper.hot.getData();
    var groupName = data[0] && data[0][2] || '';
    var typeName = data[1] && data[1][2] || '';

    var acqGroupSaveData = {
        updatelist: [{
            classes: node.classes,
            id: node.id,
            groupCode: node.code,
            groupName: groupName,
            typeName: typeName
        }]
    };

    if (node.type === 0) {
        var groupTimingInterval = data[2] && data[2][2] || '';
        var groupSavingInterval = data[3] && data[3][2] || '';
        var remark = data[4] && data[4][2] || '';
        acqGroupSaveData.updatelist[0].groupTimingInterval = groupTimingInterval;
        acqGroupSaveData.updatelist[0].groupSavingInterval = groupSavingInterval;
        acqGroupSaveData.updatelist[0].remark = remark;
    } else if (node.type === 1) {
        var remark = data[2] && data[2][2] || '';
        acqGroupSaveData.updatelist[0].remark = remark;
    }

    var protocol = node.protocol || '';
    var unitId = node.parentNode ? node.parentNode.id : 0;

    saveAcquisitionGroupConfigData(acqGroupSaveData, protocol, unitId);
}

function saveAcquisitionUnitConfigData(acqUnitSaveData, protocol, deviceType) {
    var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.updateWait});
    $.ajax({
        type: 'POST',
        url: context + '/acquisitionUnitManagerController/saveAcquisitionUnitHandsontableData',
        data: {
            data: JSON.stringify(acqUnitSaveData),
            protocol: protocol,
            deviceType: deviceType
        },
        dataType: 'json',
        success: function(response) {
            mini.unmask(document.body);
            if (response.success) {
                mini.alert(_loginUserLanguageResource.savedSuccessfully);
                refreshAcqUnitTree();
            } else {
                mini.alert('<font color="red">' + (_loginUserLanguageResource.saveFailed) + '</font>');
            }
        },
        error: function() {
            mini.unmask(document.body);
            mini.alert(_loginUserLanguageResource.requestFailed);
        }
    });
}

function saveAcquisitionGroupConfigData(acqGroupSaveData, protocol, unitId) {
    var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.updateWait });
    $.ajax({
        type: 'POST',
        url: context + '/acquisitionUnitManagerController/saveAcquisitionGroupHandsontableData',
        data: {
            data: JSON.stringify(acqGroupSaveData),
            protocol: protocol,
            unitId: unitId
        },
        dataType: 'json',
        success: function(response) {
            mini.unmask(document.body);
            if (response.success) {
                mini.alert(_loginUserLanguageResource.savedSuccessfully);
                refreshAcqUnitTree();
            } else {
                mini.alert('<font color="red">' + (_loginUserLanguageResource.saveFailed) + '</font>');
            }
        },
        error: function() {
            mini.unmask(document.body);
            mini.alert(_loginUserLanguageResource.requestFailed);
        }
    });
}
function grantAcquisitionItemsPermission(node) {
    if (!node || node.classes !== 3) {
        return;
    }

    var helper = protocolAcqUnitConfigItemsHandsontableHelper;
    if (!helper || !helper.hot) {
        return;
    }

    var data = helper.hot.getData();
    var addjson = [];
    var matrixData = '';

    for (var i = 0; i < data.length; i++) {
        var row = data[i];
        var checked = row[0] === true || row[0] === 'true';
        var dailyTotalCalculate = row[8] === true || row[8] === 'true';
        var dailyTotalCalculateName = row[9] || '';

        if (checked || dailyTotalCalculate || dailyTotalCalculateName) {
            var itemName = row[2] || '';
            var itemAddr = row[3] || '';
            var itemHighLowByte = row[11] || '';
            var resolutionMode = row[6] || '';
            var bitIndex = row[7] || '';

            var itemEnable = checked ? 1 : 0;
            var dailyTotalCalculateFlag = dailyTotalCalculate ? 1 : 0;
            if (!dailyTotalCalculateName && dailyTotalCalculate) {
                dailyTotalCalculateName = itemName + _loginUserLanguageResource.dailyTotalValue;
            }

            addjson.push(itemName);
            matrixData += itemName + ':' + itemAddr + ':' + itemHighLowByte + ':' + resolutionMode + ':' + bitIndex + '::' + dailyTotalCalculateName + ':' + dailyTotalCalculateFlag + ':' + itemEnable + '|';
        }
    }

    if (matrixData.length > 0) {
        matrixData = matrixData.substring(0, matrixData.length - 1);
    }

    var groupId = node.id;
    var groupCode = node.code;
    var protocol = node.protocol || '';

    var mask = mini.mask({ el: document.body, html: _loginUserLanguageResource.updateWait });
    $.ajax({
        url: context + '/acquisitionUnitManagerController/grantAcquisitionItemsPermission',
        type: 'POST',
        data: {
            params: addjson.join(','),
            protocol: protocol,
            groupId: groupId,
            groupCode: groupCode,
            matrixCodes: matrixData
        },
        dataType: 'json',
        success: function(response) {
            mini.unmask(document.body);
            if (response.msg === true) {
                mini.alert(_loginUserLanguageResource.savedSuccessfully );
                refreshAcqUnitTree();
            } else {
                mini.alert('<font color="red">' + (_loginUserLanguageResource.saveFailed) + '</font>');
            }
        },
        error: function() {
            mini.unmask(document.body);
            mini.alert(_loginUserLanguageResource.requestFailed);
        }
    });
}
function refreshAcqUnitTree() {
    var tree = mini.get('acqUnitListTree');
    if (tree) {
        tree.load();
    }
}

function refreshAcqUnitProtocolTree() {
    var tree = mini.get('acqUnitProtocolTree');
    if (tree) {
        tree.load();
    }
}

function addAcquisitionUnitInfo() {
    var deviceTree = mini.get('deviceTypeTree');
    if (!deviceTree) {
        return;
    }
    var selectedDeviceNode = deviceTree.getSelectedNode();
    if (!selectedDeviceNode) {
        return;
    }
    var deviceTypeIds = selectedDeviceTypeId || ''; // 已全局保存

    // 获取当前选中的协议节点（可能为协议或目录）
    var protocolTree = mini.get('acqUnitProtocolTree');
    var selectedProtocolNode = protocolTree ? protocolTree.getSelectedNode() : null;
    var protocolList = '';
    if (selectedProtocolNode) {
        if (selectedProtocolNode.classes === 1) {
            protocolList = selectedProtocolNode.code || '';
        } else if (selectedProtocolNode.classes === 0) {
            // 目录节点：收集所有子协议节点的 code
            var codes = [];
            function collect(node) {
                if (node.children && node.children.length > 0) {
                    for (var i = 0; i < node.children.length; i++) collect(node.children[i]);
                } else {
                    if (node.classes === 1 && node.code) codes.push(node.code);
                }
            }
            collect(selectedProtocolNode);
            protocolList = codes.join(',');
        }
    }

    mini.open({
        title: _loginUserLanguageResource.addAcqUnit,
        url: context + '/miniui-app/modules/driverConfig/acquisitionUnitAddWindow.jsp',
        width: 450,
        height: 350,
        modal: true,
        allowResize: true,
        onload: function() {
            var iframe = this.getIFrameEl();
            var contentWindow = iframe.contentWindow;
            contentWindow.setData({
                deviceTypeIds: deviceTypeIds,
                protocolList: protocolList
            });
            contentWindow._parentRefreshUnitTree = function() {
                var tree = mini.get('acqUnitListTree');
                if (tree) tree.load();
            };
            contentWindow._parentSetNewObject = function(name, classes) {
                window._newAcqUnitObjectName = name;
                window._newAcqUnitObjectClasses = classes;
            };
        },
        ondestroy: function(action) {
            if (action === 'ok') {
                //var tree = mini.get('acqUnitListTree');
                //if (tree) tree.load();
            }
        }
    });
}

//右键菜单打开前事件
function onAcqUnitTreeBeforeMenu(e) {
    var tree = mini.get('acqUnitListTree');
    var menu = e.sender;
    var node = tree.getSelectedNode();

    // 只对采集单元（classes==2）或采集组（classes==3）显示菜单
    if (!node || (node.classes !== 2 && node.classes !== 3)) {
        e.cancel = true;
        e.htmlEvent.preventDefault();
        return;
    }

    // 更新菜单文字（国际化）
    var deleteText = _loginUserLanguageResource.deleteData;
    document.getElementById('acqUnitTreeMenuDeleteText').textContent = deleteText;

    // 根据权限禁用菜单项
    var deleteItem = mini.getbyName('delete', menu);
    if (!editFlag) {
        deleteItem.disable();
    } else {
        deleteItem.enable();
    }
}

function addAcquisitionGroupInfo() {
    var deviceTree = mini.get('deviceTypeTree');
    if (!deviceTree) {
        return;
    }
    var selectedDeviceNode = deviceTree.getSelectedNode();
    if (!selectedDeviceNode) {
        return;
    }
    var deviceTypeIds = selectedDeviceTypeId || '';

    // 获取当前选中的协议节点（可能为协议或目录）
    var protocolTree = mini.get('acqUnitProtocolTree');
    var selectedProtocolNode = protocolTree ? protocolTree.getSelectedNode() : null;
    var protocolList = '';
    if (selectedProtocolNode) {
        if (selectedProtocolNode.classes === 1) {
            protocolList = selectedProtocolNode.code || '';
        } else if (selectedProtocolNode.classes === 0) {
            var codes = [];
            function collect(node) {
                if (node.children && node.children.length > 0) {
                    for (var i = 0; i < node.children.length; i++) collect(node.children[i]);
                } else {
                    if (node.classes === 1 && node.code) codes.push(node.code);
                }
            }
            collect(selectedProtocolNode);
            protocolList = codes.join(',');
        }
    }

    // 获取当前选中的单元（如果有）
    var unitTree = mini.get('acqUnitListTree');
    var selectedUnitNode = unitTree ? unitTree.getSelectedNode() : null;
    var unitList = '';
    if (selectedUnitNode && selectedUnitNode.classes === 2) {
        unitList = selectedUnitNode.code || '';
    }

    mini.open({
        title: _loginUserLanguageResource.addAcqGroup,
        url: context + '/miniui-app/modules/driverConfig/acquisitionGroupAddWindow.jsp',
        width: 450,
        height: 420,
        modal: true,
        allowResize: true,
        onload: function() {
            var iframe = this.getIFrameEl();
            var contentWindow = iframe.contentWindow;
            contentWindow.setData({
                deviceTypeIds: deviceTypeIds,
                protocolList: protocolList,
                unitList: unitList,
                type: 0  // 采集组
            });
            contentWindow._parentRefreshUnitTree = function() {
                var tree = mini.get('acqUnitListTree');
                if (tree) tree.load();
            };
            contentWindow._parentSetNewObject = function(name, classes) {
                window._newAcqUnitObjectName = name;
                window._newAcqUnitObjectClasses = classes;
            };
        },
        ondestroy: function(action) {
            if (action === 'ok') {
                //var tree = mini.get('acqUnitListTree');
                //if (tree) tree.load();
            }
        }
    });
}

function addControlGroupInfo() {
    var deviceTree = mini.get('deviceTypeTree');
    if (!deviceTree) {
        return;
    }
    var selectedDeviceNode = deviceTree.getSelectedNode();
    if (!selectedDeviceNode) {
        return;
    }
    var deviceTypeIds = selectedDeviceTypeId || '';

    var protocolTree = mini.get('acqUnitProtocolTree');
    var selectedProtocolNode = protocolTree ? protocolTree.getSelectedNode() : null;
    var protocolList = '';
    if (selectedProtocolNode) {
        if (selectedProtocolNode.classes === 1) {
            protocolList = selectedProtocolNode.code || '';
        } else if (selectedProtocolNode.classes === 0) {
            var codes = [];
            function collect(node) {
                if (node.children && node.children.length > 0) {
                    for (var i = 0; i < node.children.length; i++) collect(node.children[i]);
                } else {
                    if (node.classes === 1 && node.code) codes.push(node.code);
                }
            }
            collect(selectedProtocolNode);
            protocolList = codes.join(',');
        }
    }

    var unitTree = mini.get('acqUnitListTree');
    var selectedUnitNode = unitTree ? unitTree.getSelectedNode() : null;
    var unitList = '';
    if (selectedUnitNode && selectedUnitNode.classes === 2) {
        unitList = selectedUnitNode.code || '';
    }

    mini.open({
        title: _loginUserLanguageResource.addCtrlGroup,
        url: context + '/miniui-app/modules/driverConfig/acquisitionGroupAddWindow.jsp',
        width: 450,
        height: 380,  // 控制组少两个字段，高度稍小
        modal: true,
        allowResize: true,
        onload: function() {
            var iframe = this.getIFrameEl();
            var contentWindow = iframe.contentWindow;
            contentWindow.setData({
                deviceTypeIds: deviceTypeIds,
                protocolList: protocolList,
                unitList: unitList,
                type: 1  // 控制组
            });
            contentWindow._parentRefreshUnitTree = function() {
                var tree = mini.get('acqUnitListTree');
                if (tree) tree.load();
            };
            contentWindow._parentSetNewObject = function(name, classes) {
                window._newAcqUnitObjectName = name;
                window._newAcqUnitObjectClasses = 3;
            };
        },
        ondestroy: function(action) {
            if (action === 'ok') {
                //var tree = mini.get('acqUnitListTree');
                //if (tree) tree.load();
            }
        }
    });
}

// 删除采集单元/组节点
function deleteAcqUnitNode(e) {
    var tree = mini.get('acqUnitListTree');
    var node = tree.getSelectedNode();
    if (!node) {
        return;
    }

    var nodeId = node.id;
    var nodeText = node.text;
    var classes = node.classes;

    // 确认删除
    mini.confirm(_loginUserLanguageResource.confirmDelete, 
                 _loginUserLanguageResource.confirm, 
                 function(action) {
        if (action === 'ok') {
            if (classes === 2) {
                // 删除采集单元
                var acqUnitSaveData = {
                    delidslist: [nodeId]
                };
                // 获取 protocol 和 deviceType
                var protocol = node.protocol || '';
                var deviceType = node.deviceType || 0;
                // 从父节点获取 deviceType（如果节点本身没有）
                if (!deviceType && node.parentNode) {
                    deviceType = node.parentNode.deviceType || 0;
                }
                saveAcquisitionUnitConfigData(acqUnitSaveData, protocol, deviceType);
            } else if (classes === 3) {
                // 删除采集组
                var acqGroupSaveData = {
                    delidslist: [nodeId]
                };
                var protocol = node.protocol || '';
                var unitId = 0;
                if (node.parentNode) {
                    unitId = node.parentNode.id || 0;
                }
                saveAcquisitionGroupConfigData(acqGroupSaveData, protocol, unitId);
            }
        }
    });
}

function openExportAcqUnitWindow() {
    // 获取设备类型树选中的节点（用于过滤）
    var deviceTree = mini.get('deviceTypeTree');
    if (!deviceTree) {
        return;
    }
    var selectedNode = deviceTree.getSelectedNode();
    if (!selectedNode) {
        return;
    }
    // 获取当前选中的设备类型ID（含子节点）
    var deviceTypeIds = selectedDeviceTypeId || '';

    mini.open({
        title: _loginUserLanguageResource.exportAcqUnit,
        url: context + '/miniui-app/modules/driverConfig/exportAcqUnitWindow.jsp',
        width: 400,
        height: 600,
        modal: true,
        allowResize: true,
        maxable: true,
        onload: function() {
            var iframe = this.getIFrameEl();
            var contentWindow = iframe.contentWindow;
            contentWindow.setData({
                deviceTypeIds: deviceTypeIds
            });
        }
    });
}

function openImportAcqUnitWindow() {
    var deviceTree = mini.get('deviceTypeTree');
    if (!deviceTree) {
        return;
    }
    var selectedNode = deviceTree.getSelectedNode();
    if (!selectedNode) {
        return;
    }
    var deviceTypeId = selectedNode.deviceTypeId;
    var deviceTypeName = getNodePath(deviceTree, selectedNode);

    mini.open({
        title: _loginUserLanguageResource.importAcqUnit,
        url: context + '/miniui-app/modules/driverConfig/importAcqUnitWindow.jsp',
        width: '90%',
        height: '80%',
        modal: true,
        allowResize: true,
        maxable: true,
        onload: function() {
            var iframe = this.getIFrameEl();
            var contentWindow = iframe.contentWindow;
            contentWindow.setData({
                deviceTypeId: deviceTypeId,
                deviceTypeName: deviceTypeName
            });
            // 暴露刷新父页面单元列表树的函数
            contentWindow.parent.refreshAcqUnitTree = function() {
                var tree = mini.get('acqUnitListTree');
                if (tree) tree.load();
            };
        },
        ondestroy: function() {
            // 可选刷新
        }
    });
}