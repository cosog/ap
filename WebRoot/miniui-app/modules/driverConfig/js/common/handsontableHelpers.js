var ProtocolPropertiesHandsontableHelper = {
            createNew: function(divid) {
                var protocolPropertiesHandsontableHelper = {};
                protocolPropertiesHandsontableHelper.hot = '';
                protocolPropertiesHandsontableHelper.classes = null;
                protocolPropertiesHandsontableHelper.divid = divid;
                protocolPropertiesHandsontableHelper.validresult = true; //数据校验
                protocolPropertiesHandsontableHelper.colHeaders = [];
                protocolPropertiesHandsontableHelper.columns = [];
                protocolPropertiesHandsontableHelper.AllData = [];

                protocolPropertiesHandsontableHelper.addBoldBg = function(instance, td, row, col, prop, value, cellProperties) {
                    Handsontable.renderers.TextRenderer.apply(this, arguments);
                    td.style.backgroundColor = 'rgb(245, 245, 245)';
                    td.style.whiteSpace = 'nowrap'; //文本不换行
                    td.style.overflow = 'hidden'; //超出部分隐藏
                    td.style.textOverflow = 'ellipsis'; //使用省略号表示溢出的文本
                }

                protocolPropertiesHandsontableHelper.addCellStyle = function(instance, td, row, col, prop, value, cellProperties) {
                    Handsontable.renderers.TextRenderer.apply(this, arguments);
                    td.style.whiteSpace = 'nowrap'; //文本不换行
                    td.style.overflow = 'hidden'; //超出部分隐藏
                    td.style.textOverflow = 'ellipsis'; //使用省略号表示溢出的文本
                }

                protocolPropertiesHandsontableHelper.createTable = function(data) {
                    $('#' + protocolPropertiesHandsontableHelper.divid).empty();
                    var hotElement = document.querySelector('#' + protocolPropertiesHandsontableHelper.divid);
                    protocolPropertiesHandsontableHelper.hot = new Handsontable(hotElement, {
                        licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                        theme: 'ht-theme-classic',
                        data: data,
                        colWidths: [1, 8, 10],
                        columns: protocolPropertiesHandsontableHelper.columns,
                        stretchH: 'all', //延伸列的宽度, last:延伸最后一列,all:延伸所有列,none默认不延伸
                        autoWrapRow: true,
                        rowHeaders: false, //显示行头
                        colHeaders: protocolPropertiesHandsontableHelper.colHeaders, //显示列头
                        columnSorting: true, //允许排序
                        sortIndicator: true,
                        manualColumnResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                        manualRowResize: true, //当值为true时，允许拖动，当为false时禁止拖动
                        filters: true,
                        renderAllRows: true,
                        search: true,
                        contextMenu: {
                            items: {
                                "copy": {
                                    name: loginUserLanguageResource.contextMenu_copy
                                },
                                "cut": {
                                    name: loginUserLanguageResource.contextMenu_cut
                                }
                            }
                        },
                        cells: function(row, col, prop) {
                            var cellProperties = {};
                            var visualRowIndex = this.instance.toVisualRow(row);
                            var visualColIndex = this.instance.toVisualColumn(col);

                            var protocolConfigModuleEditFlag = 1;
                            if (protocolConfigModuleEditFlag == 1) {
                                if (protocolPropertiesHandsontableHelper.classes === 0) {
                                    cellProperties.editor = false;
                                    cellProperties.renderer = protocolPropertiesHandsontableHelper.addBoldBg;
                                } else if (protocolPropertiesHandsontableHelper.classes === 1) {
                                    if (visualColIndex == 0 || visualColIndex == 1) {
                                        cellProperties.editor = false;
                                        cellProperties.renderer = protocolPropertiesHandsontableHelper.addBoldBg;
                                    } else if (visualColIndex === 2 && visualRowIndex === 0) {
                                        this.validator = function(val, callback) {
                                            return handsontableDataCheck_NotNull(val, callback, row, col, protocolPropertiesHandsontableHelper);
                                        }
                                        cellProperties.renderer = protocolPropertiesHandsontableHelper.addCellStyle;
                                    } else if (visualColIndex === 2 && visualRowIndex === 1) {
                                        this.validator = function(val, callback) {
                                            return handsontableDataCheck_Num_Nullable(val, callback, row, col, protocolPropertiesHandsontableHelper);
                                        }
                                        cellProperties.renderer = protocolPropertiesHandsontableHelper.addCellStyle;
                                    } else if (visualColIndex === 2 && (visualRowIndex === 2 || visualRowIndex === 3)) {
                                        cellProperties.editor = false;
                                        cellProperties.renderer = protocolPropertiesHandsontableHelper.addBoldBg;
                                    }
                                }
                            } else {
                                cellProperties.editor = false;
                                cellProperties.renderer = protocolPropertiesHandsontableHelper.addBoldBg;
                            }

                            return cellProperties;
                        }
                    });
                }
                protocolPropertiesHandsontableHelper.saveData = function() {}
                protocolPropertiesHandsontableHelper.clearContainer = function() {
                    protocolPropertiesHandsontableHelper.AllData = [];
                }
                return protocolPropertiesHandsontableHelper;
            }
        };

var ProtocolItemsConfigHandsontableHelper = {
        createNew: function(divid) {
            var helper = {};
            helper.hot = null;
            helper.divid = divid;
            helper.validresult = true;
            helper.colHeaders = [];
            helper.columns = [];
            helper.AllData = [];
            helper.Data = [];
            helper.titleDataMap = new Map();
            helper.addressDataMap = new Map();

            helper.initTitleDataMap = function(data) {
                helper.titleDataMap.clear();
                data.forEach((row, index) => {
                    const value = row.title;
                    if (row.title == undefined || !value) return;
                    if (!helper.titleDataMap.has(value)) {
                        helper.titleDataMap.set(value, [index]);
                    } else {
                        helper.titleDataMap.get(value).push(index);
                    }
                });
            };

            helper.getDuplicateCount = function() {
                var count = 0;
                for (var [value, indexes] of helper.titleDataMap.entries()) {
                    if (indexes.length > 1) count += indexes.length;
                }
                return count;
            };

            helper.getDuplicateRowList = function() {
                var list = [];
                for (var [value, indexes] of helper.titleDataMap.entries()) {
                    if (indexes.length > 1) {
                        for (var i = 0; i < indexes.length; i++) list.push(indexes[i]);
                    }
                }
                return list;
            };

            helper.initAddressDataMap = function(data) {
                helper.addressDataMap.clear();
                data.forEach((row, index) => {
                    var value = "";
                    var addr = row.addr + "";
                    var highLowByte = row.highLowByte + "";
                    if (addr != '' || highLowByte != '') {
                        value = addr + '_' + highLowByte;
                    }
                    if ((row.addr == undefined && row.highLowByte == undefined) || !value) return;

                    if (!helper.addressDataMap.has(value)) {
                        helper.addressDataMap.set(value, [index]);
                    } else {
                        helper.addressDataMap.get(value).push(index);
                    }
                });
            };

            helper.getAddrDuplicateCount = function() {
                var count = 0;
                for (var [key, indexes] of helper.addressDataMap.entries()) {
                    if (indexes.length > 1) count += indexes.length;
                }
                return count;
            };

            helper.getAddrDuplicateRowList = function() {
                var list = [];
                for (var [key, indexes] of helper.addressDataMap.entries()) {
                    if (indexes.length > 1) {
                        for (var i = 0; i < indexes.length; i++) list.push(indexes[i]);
                    }
                }
                return list;
            };

            helper.uniqueTitleRenderer = function(instance, td, row, col, prop, value, cellProperties) {
                if (cellProperties.type === 'checkbox') {
                    Handsontable.renderers.CheckboxRenderer.apply(this, arguments);
                } else if (cellProperties.type === 'dropdown') {
                    Handsontable.renderers.DropdownRenderer.apply(this, arguments);
                } else {
                    Handsontable.renderers.TextRenderer.apply(this, arguments);
                }
                if (cellProperties.type !== 'checkbox') {
                    td.style.whiteSpace = 'nowrap';
                    td.style.overflow = 'hidden';
                    td.style.textOverflow = 'ellipsis';
                }
                if (prop === 'title') {
                    if (value && helper.titleDataMap.has(value)) {
                        var rows = helper.titleDataMap.get(value);
                        if (rows.length > 1 && rows.includes(row)) {
                            td.style.backgroundColor = '#FF4C42';
                        }
                    }
                } else if (prop === 'addr' || prop === 'highLowByte') {
                    var oldAddr = (prop === 'addr') ? value : instance.getDataAtRowProp(row, 'addr');
                    var oldHigh = (prop === 'highLowByte') ? value : instance.getDataAtRowProp(row, 'highLowByte');
                    if (oldAddr !== '' || oldHigh !== '') {
                        var key = oldAddr + '_' + oldHigh;
                        if (helper.addressDataMap.has(key)) {
                            var rows = helper.addressDataMap.get(key);
                            if (rows.length > 1 && rows.includes(row)) {
                                td.style.backgroundColor = '#FF4C42';
                            }
                        }
                    }
                }
            };

            helper.addCellStyle = function(instance, td, row, col, prop, value, cellProperties) {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                td.style.whiteSpace = 'nowrap';
                td.style.overflow = 'hidden';
                td.style.textOverflow = 'ellipsis';
            };

            helper.addBoldBg = function(instance, td, row, col, prop, value, cellProperties) {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                td.style.backgroundColor = 'rgb(245, 245, 245)';
                td.style.whiteSpace = 'nowrap';
                td.style.overflow = 'hidden';
                td.style.textOverflow = 'ellipsis';
            };

            helper.createTable = function(data) {
                $('#' + helper.divid).empty();
                var hotElement = document.querySelector('#' + helper.divid);
                helper.hot = new Handsontable(hotElement, {
                    licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                    theme: 'ht-theme-classic',
                    data: data,
                    hiddenColumns: {
                        columns: [0, 3],
                        indicators: false,
                        copyPasteEnabled: false
                    },
                    colWidths: [50, 200, 80, 80, 90, 90, 80, 80, 90, 80, 80, 80, 160],
                    columns: helper.columns,
                    stretchH: 'all',
                    autoWrapRow: true,
                    rowHeaders: true,
                    bindRowsWithHeaders: 'strict',
                    nestedHeaders: helper.colHeaders,
                    columnHeaderHeight: 28,
                    columnSorting: true,
                    sortIndicator: true,
                    manualColumnResize: true,
                    manualRowResize: true,
                    filters: true,
                    renderAllRows: true,
                    search: true,
                    outsideClickDeselects: false,
                    contextMenu: {
                        items: {
                            "row_above": {
                                name: (_loginUserLanguageResource.contextMenu_insertRowAbove || 'Insert row above')
                            },
                            "row_below": {
                                name: (_loginUserLanguageResource.contextMenu_insertRowBelow || 'Insert row below')
                            },
                            "col_left": {
                                name: (_loginUserLanguageResource.contextMenu_insertColumnLeft || 'Insert column left')
                            },
                            "col_right": {
                                name: (_loginUserLanguageResource.contextMenu_insertColumnRight || 'Insert column right')
                            },
                            "remove_row": {
                                name: (_loginUserLanguageResource.contextMenu_removeRow || 'Remove row')
                            },
                            "remove_col": {
                                name: (_loginUserLanguageResource.contextMenu_removeColumn || 'Remove column')
                            },
                            "merge_cell": {
                                name: (_loginUserLanguageResource.contextMenu_mergeCell || 'Merge cells')
                            },
                            "copy": {
                                name: (_loginUserLanguageResource.contextMenu_copy || 'Copy')
                            },
                            "cut": {
                                name: (_loginUserLanguageResource.contextMenu_cut || 'Cut')
                            }
                        }
                    },
                    cells: function(row, col, prop) {
                        var cellProperties = {};
                        var visualRowIndex = this.instance.toVisualRow(row);
                        var visualColIndex = this.instance.toVisualColumn(col);
                        // 权限控制（可从全局变量读取）
                        if (!editFlag) {
                            cellProperties.editor = false;
                        } else {
                            if (visualColIndex === 0) {
                                cellProperties.editor = false;
                            }
                        }
                        if (prop === 'storeDataType') {
                            var highLowByte = this.instance.getDataAtRowProp(row, 'highLowByte')
                            this.type = 'dropdown';
                            this.strict = true;
                            this.allowInvalid = false;
                            if (isNotVal(highLowByte)) {
                                this.source = ['bit', 'byte'];
                            } else {
                                this.source = ['bit', 'byte', 'int16', 'uint16', 'float32', 'float64', 'bcd'];
                            }
                        }
                        if (visualColIndex === 0) {
                            cellProperties.renderer = helper.addBoldBg;
                        } else {
                            cellProperties.renderer = helper.uniqueTitleRenderer;
                        }
                        return cellProperties;
                    },
                    afterChange: function(changes, source) {
                        if (!changes) return;
                        var needUpdate = false;
                        changes.forEach(function(change) {
                            var row = change[0],
                                prop = change[1],
                                oldVal = change[2],
                                newVal = change[3];
                            if (prop === 'title') {
                                // 更新 titleDataMap
                                if (oldVal && helper.titleDataMap.has(oldVal)) {
                                    var rows = helper.titleDataMap.get(oldVal);
                                    var idx = rows.indexOf(row);
                                    if (idx !== -1) rows.splice(idx, 1);
                                    if (rows.length === 0) helper.titleDataMap.delete(oldVal);
                                }
                                if (newVal) {
                                    if (!helper.titleDataMap.has(newVal)) {
                                        helper.titleDataMap.set(newVal, [row]);
                                    } else {
                                        var rows = helper.titleDataMap.get(newVal);
                                        if (!rows.includes(row)) rows.push(row);
                                    }
                                }
                                if (oldVal !== newVal) needUpdate = true;
                            } else if (prop === 'addr' || prop === 'highLowByte') {
                                var oldAddr = (prop === 'addr') ? oldVal : helper.hot.getDataAtRowProp(row, 'addr');
                                var oldHigh = (prop === 'highLowByte') ? oldVal : helper.hot.getDataAtRowProp(row, 'highLowByte');
                                var newAddr = (prop === 'addr') ? newVal : helper.hot.getDataAtRowProp(row, 'addr');
                                var newHigh = (prop === 'highLowByte') ? newVal : helper.hot.getDataAtRowProp(row, 'highLowByte');
                                var oldKey = (oldAddr !== '' || oldHigh !== '') ? oldAddr + '_' + oldHigh : null;
                                var newKey = (newAddr !== '' || newHigh !== '') ? newAddr + '_' + newHigh : null;
                                if (oldKey && helper.addressDataMap.has(oldKey)) {
                                    var rows = helper.addressDataMap.get(oldKey);
                                    var idx = rows.indexOf(row);
                                    if (idx !== -1) rows.splice(idx, 1);
                                    if (rows.length === 0) helper.addressDataMap.delete(oldKey);
                                }
                                if (newKey) {
                                    if (!helper.addressDataMap.has(newKey)) {
                                        helper.addressDataMap.set(newKey, [row]);
                                    } else {
                                        var rows = helper.addressDataMap.get(newKey);
                                        if (!rows.includes(row)) rows.push(row);
                                    }
                                }
                                if (oldKey !== newKey) needUpdate = true;

                                // 若当前选中行变化，刷新含义表
                                if (prop === 'highLowByte') {
                                    var selectedRow = parseInt($('#ModbusProtocolAddrMappingItemsSelectRow_Id').val() || 0);
                                    if (selectedRow === row && typeof protocolItemsMeaningConfigHandsontableHelper !== 'undefined' && protocolItemsMeaningConfigHandsontableHelper && protocolItemsMeaningConfigHandsontableHelper.hot) {
                                        var resolutionMode = helper.hot.getDataAtRowProp(row, 'resolutionMode');
                                        if (resolutionMode === (_loginUserLanguageResource.switchingValue || 'Switching')) {
                                            var itemTitle = helper.hot.getDataAtRowProp(row, 'title');
                                            var itemAddr = helper.hot.getDataAtRowProp(row, 'addr');
                                            var highLowByte = newVal;
                                            var quantity = helper.hot.getDataAtRowProp(row, 'quantity');
                                            var protocolCode = getCurrentProtocolCode();
                                            CreateModbusProtocolAddrMappingItemsMeaningConfigInfoTable(protocolCode, itemTitle, itemAddr, highLowByte, resolutionMode, quantity, true);
                                        }
                                    }
                                }
                            } else if (prop === 'resolutionMode') {
                                var selectedRow = parseInt($('#ModbusProtocolAddrMappingItemsSelectRow_Id').val() || 0);
                                if (selectedRow === row && typeof protocolItemsMeaningConfigHandsontableHelper !== 'undefined' && protocolItemsMeaningConfigHandsontableHelper && protocolItemsMeaningConfigHandsontableHelper.hot) {
                                    var resolutionMode = newVal;
                                    var itemTitle = helper.hot.getDataAtRowProp(row, 'title');
                                    var itemAddr = helper.hot.getDataAtRowProp(row, 'addr');
                                    var highLowByte = helper.hot.getDataAtRowProp(row, 'highLowByte');
                                    var quantity = helper.hot.getDataAtRowProp(row, 'quantity');
                                    var protocolCode = getCurrentProtocolCode();
                                    CreateModbusProtocolAddrMappingItemsMeaningConfigInfoTable(protocolCode, itemTitle, itemAddr, highLowByte, resolutionMode, quantity, true);
                                }
                            } else if (prop === 'quantity') {
                                var selectedRow = parseInt($('#ModbusProtocolAddrMappingItemsSelectRow_Id').val() || 0);
                                if (selectedRow === row && typeof protocolItemsMeaningConfigHandsontableHelper !== 'undefined' && protocolItemsMeaningConfigHandsontableHelper && protocolItemsMeaningConfigHandsontableHelper.hot) {
                                    var resolutionMode = helper.hot.getDataAtRowProp(row, 'resolutionMode');
                                    if (resolutionMode === (_loginUserLanguageResource.switchingValue || 'Switching')) {
                                        var itemTitle = helper.hot.getDataAtRowProp(row, 'title');
                                        var itemAddr = helper.hot.getDataAtRowProp(row, 'addr');
                                        var highLowByte = helper.hot.getDataAtRowProp(row, 'highLowByte');
                                        var quantity = newVal;
                                        var protocolCode = getCurrentProtocolCode();
                                        CreateModbusProtocolAddrMappingItemsMeaningConfigInfoTable(protocolCode, itemTitle, itemAddr, highLowByte, resolutionMode, quantity, true);
                                    }
                                }
                            }
                        });
                        if (needUpdate) helper.hot.render();
                    },
                    afterSelectionEnd: function(row, column, row2, column2, selectionLayerLevel) {
                        if (row < 0 && row2 < 0) {
                            // 只选中表头
                            $('#ModbusProtocolAddrMappingItemsSelectRow_Id').val('');
                            CreateModbusProtocolAddrMappingItemsMeaningConfigInfoTable('', '', '', '', '', '', true);
                            return;
                        }
                        var startRow = (row < 0) ? 0 : row;
                        if (row2 < 0) row2 = 0;
                        if (row > row2) {
                            startRow = row2;
                            row2 = row;
                        }
                        var selectedRow = parseInt($('#ModbusProtocolAddrMappingItemsSelectRow_Id').val() || 0);
                        if (selectedRow !== startRow) {
                            $('#ModbusProtocolAddrMappingItemsSelectRow_Id').val(startRow);
                            var protocolCode = getCurrentProtocolCode();
                            var itemTitle = helper.hot.getDataAtRowProp(startRow, 'title');
                            var itemAddr = helper.hot.getDataAtRowProp(startRow, 'addr');
                            var highLowByte = helper.hot.getDataAtRowProp(startRow, 'highLowByte');
                            var resolutionMode = helper.hot.getDataAtRowProp(startRow, 'resolutionMode');
                            var quantity = helper.hot.getDataAtRowProp(startRow, 'quantity');
                            CreateModbusProtocolAddrMappingItemsMeaningConfigInfoTable(protocolCode, itemTitle, itemAddr, highLowByte, resolutionMode, quantity, true);
                        }
                    }
                });
            };
            return helper;
        }
    };

var ProtocolItemsMeaningConfigHandsontableHelper = {
        createNew: function(divid) {
            var helper = {};
            helper.hot = null;
            helper.divid = divid;
            helper.validresult = true;
            helper.colHeaders = [];
            helper.columns = [];
            helper.AllData = [];
            helper.itemResolutionMode = 2;
            helper.hiddenColumns = [];
            helper.contextMenu = null;

            helper.addCellStyle = function(instance, td, row, col, prop, value, cellProperties) {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                td.style.whiteSpace = 'nowrap';
                td.style.overflow = 'hidden';
                td.style.textOverflow = 'ellipsis';
            };

            helper.addBoldBg = function(instance, td, row, col, prop, value, cellProperties) {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                td.style.backgroundColor = 'rgb(245, 245, 245)';
                td.style.whiteSpace = 'nowrap';
                td.style.overflow = 'hidden';
                td.style.textOverflow = 'ellipsis';
            };

            helper.createTable = function(data) {
                $('#' + helper.divid).empty();
                var hotElement = document.querySelector('#' + helper.divid);
                helper.hot = new Handsontable(hotElement, {
                    licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                    theme: 'ht-theme-classic',
                    data: data,
                    hiddenColumns: {
                        columns: helper.hiddenColumns,
                        indicators: false,
                        copyPasteEnabled: false
                    },
                    colWidths: [1, 3],
                    columns: helper.columns,
                    stretchH: 'all',
                    autoWrapRow: true,
                    rowHeaders: false,
                    colHeaders: helper.colHeaders,
                    columnSorting: true,
                    sortIndicator: true,
                    manualColumnResize: true,
                    manualRowResize: true,
                    filters: true,
                    renderAllRows: true,
                    search: true,
                    contextMenu: helper.contextMenu,
                    cells: function(row, col, prop) {
                        var cellProperties = {};
                        if (!editFlag) {
                            cellProperties.editor = false;
                            cellProperties.renderer = helper.addCellStyle;
                        } else {
                            if (helper.itemResolutionMode === 0 && prop === 'title') {
                                cellProperties.editor = false;
                                cellProperties.renderer = helper.addBoldBg;
                            } else {
                                cellProperties.renderer = helper.addCellStyle;
                            }
                        }
                        return cellProperties;
                    }
                });
            };
            return helper;
        }
    };
    var ProtocolSwitchingValueBitStatusConfigHandsontableHelper = {
        createNew: function(divid) {
            var helper = {};
            helper.hot = null;
            helper.divid = divid;
            helper.validresult = true;
            helper.colHeaders = [];
            helper.columns = [];
            helper.AllData = [];

            helper.addCellStyle = function(instance, td, row, col, prop, value, cellProperties) {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                td.style.whiteSpace = 'nowrap';
                td.style.overflow = 'hidden';
                td.style.textOverflow = 'ellipsis';
            };

            helper.addBoldBg = function(instance, td, row, col, prop, value, cellProperties) {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                td.style.backgroundColor = 'rgb(245, 245, 245)';
                td.style.whiteSpace = 'nowrap';
                td.style.overflow = 'hidden';
                td.style.textOverflow = 'ellipsis';
            };

            helper.createTable = function(data) {
                $('#' + helper.divid).empty();
                var hotElement = document.querySelector('#' + helper.divid);
                helper.hot = new Handsontable(hotElement, {
                    licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                    theme: 'ht-theme-classic',
                    data: data,
                    hiddenColumns: {
                        columns: [2, 3],
                        indicators: false,
                        copyPasteEnabled: false
                    },
                    colWidths: [2, 5],
                    columns: helper.columns,
                    stretchH: 'all',
                    autoWrapRow: true,
                    rowHeaders: false,
                    colHeaders: helper.colHeaders,
                    columnSorting: true,
                    sortIndicator: true,
                    manualColumnResize: true,
                    manualRowResize: true,
                    filters: true,
                    renderAllRows: true,
                    search: true,
                    cells: function(row, col, prop) {
                        var cellProperties = {};
                        if (!editFlag) {
                            cellProperties.editor = false;
                            cellProperties.renderer = helper.addCellStyle;
                        } else {
                            if (prop === 'title') {
                                cellProperties.editor = false;
                                cellProperties.renderer = helper.addBoldBg;
                            } else {
                                cellProperties.renderer = helper.addCellStyle;
                            }
                        }
                        return cellProperties;
                    }
                });
            };
            return helper;
        }
    };
    
 // ================================================================
    // 扩展字段 - 数值运算表格 Helper
    // ================================================================
    var ProtocolExtendedFieldConfigHandsontableHelper = {
        createNew: function(divid) {
            var helper = {};
            helper.hot = null;
            helper.divid = divid;
            helper.validresult = true;
            helper.colHeaders = [];
            helper.columns = [];
            helper.AllData = [];

            helper.addCellStyle = function(instance, td, row, col, prop, value, cellProperties) {
                if (cellProperties.type === 'checkbox') {
                    Handsontable.renderers.CheckboxRenderer.apply(this, arguments);
                } else if (cellProperties.type === 'dropdown') {
                    Handsontable.renderers.DropdownRenderer.apply(this, arguments);
                } else {
                    Handsontable.renderers.TextRenderer.apply(this, arguments);
                }
                if (cellProperties.type !== 'checkbox') {
                    td.style.whiteSpace = 'nowrap';
                    td.style.overflow = 'hidden';
                    td.style.textOverflow = 'ellipsis';
                }
            };

            helper.placeholderRenderer = function(instance, td, row, col, prop, value, cellProperties) {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                td.style.whiteSpace = 'nowrap';
                td.style.overflow = 'hidden';
                td.style.textOverflow = 'ellipsis';

                var children = td.children;
                if (children.length > 0) {
                    for (var i = 0; i < children.length; i++) {
                        var child = children[i];
                        child.style.whiteSpace = 'nowrap';
                        child.style.overflow = 'hidden';
                        child.style.textOverflow = 'ellipsis';
                        child.style.display = 'block';
                        child.style.maxWidth = '100%';
                    }
                }

                if (value === null || value === '') {
                    td.style.color = 'gray';
                    td.style.fontStyle = 'italic';
                    td.innerHTML = (_loginUserLanguageResource.doubleClickCellTip || 'Double click') + '...';
                }
            };

            helper.createTable = function(data) {
                $('#' + helper.divid).empty();
                var hotElement = document.querySelector('#' + helper.divid);
                helper.hot = new Handsontable(hotElement, {
                    licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                    theme: 'ht-theme-classic',
                    data: data,
                    hiddenColumns: {
                        columns: [0],
                        indicators: false,
                        copyPasteEnabled: false
                    },
                    colWidths: [50, 200, 200, 80, 200, 80, 80, 80, 150],
                    columns: helper.columns,
                    stretchH: 'all',
                    autoWrapRow: true,
                    rowHeaders: true,
                    colHeaders: helper.colHeaders,
                    columnSorting: true,
                    sortIndicator: true,
                    manualColumnResize: true,
                    manualRowResize: true,
                    filters: true,
                    renderAllRows: true,
                    search: true,
                    contextMenu: {
                        items: {
                            "row_above": {
                                name: (_loginUserLanguageResource.contextMenu_insertRowAbove || 'Insert row above')
                            },
                            "row_below": {
                                name: (_loginUserLanguageResource.contextMenu_insertRowBelow || 'Insert row below')
                            },
                            "col_left": {
                                name: (_loginUserLanguageResource.contextMenu_insertColumnLeft || 'Insert column left')
                            },
                            "col_right": {
                                name: (_loginUserLanguageResource.contextMenu_insertColumnRight || 'Insert column right')
                            },
                            "remove_row": {
                                name: (_loginUserLanguageResource.contextMenu_removeRow || 'Remove row')
                            },
                            "remove_col": {
                                name: (_loginUserLanguageResource.contextMenu_removeColumn || 'Remove column')
                            },
                            "merge_cell": {
                                name: (_loginUserLanguageResource.contextMenu_mergeCell || 'Merge cells')
                            },
                            "copy": {
                                name: (_loginUserLanguageResource.contextMenu_copy || 'Copy')
                            },
                            "cut": {
                                name: (_loginUserLanguageResource.contextMenu_cut || 'Cut')
                            }
                        }
                    },
                    cells: function(row, col, prop) {
                        var cellProperties = {};
                        if (!editFlag) {
                            cellProperties.editor = false;
                        }
                        return cellProperties;
                    },
                    afterBeginEditing: function(row, column) {
                        var cellMeta = helper.hot.getCellMeta(row, column);
                        var selectedItemName = helper.hot.getDataAtCell(row, column);
                        if (cellMeta.prop.toUpperCase() === 'title1'.toUpperCase() ||
                            cellMeta.prop.toUpperCase() === 'title2'.toUpperCase()) {

                            var tree = mini.get('protocolTree');
                            var selectedNode = tree.getSelectedNode();
                            if (!selectedNode || selectedNode.classes !== 1) {
                                return;
                            }
                            var protocolCode = selectedNode.code || '';

                            mini.open({
                                title: _loginUserLanguageResource.config,
                                url: context + '/miniui-app/modules/driverConfig/protocolExtendedFieldSelectWindow.jsp',
                                width: 600,
                                height: 800,
                                modal: true,
                                allowResize: true,
                                onload: function() {
                                    var iframe = this.getIFrameEl();
                                    var contentWindow = iframe.contentWindow;
                                    contentWindow.setData({
                                        protocolCode: protocolCode,
                                        row: row,
                                        col: column,
                                        fieldType: 0,
                                        currentValue: selectedItemName || ''
                                    });
                                    contentWindow.parent.setExtendedFieldValue = function(r, c, value) {
                                        helper.hot.setDataAtCell(r, c, value);
                                        //helper.hot.render();
                                    };
                                },
                                ondestroy: function(action) {
                                    // 关闭后无额外操作
                                }
                            });
                        }
                    },
                    afterOnCellMouseOver: function(event, coords, TD) {

                    }
                });
            };
            return helper;
        }
    };
  //================================================================
    //扩展字段 - 高低字节主表 Helper
    //================================================================
    var ProtocolExtendedFieldHighLowByteConfigHandsontableHelper = {
        createNew: function(divid) {
            var helper = {};
            helper.hot = null;
            helper.divid = divid;
            helper.validresult = true;
            helper.colHeaders = [];
            helper.columns = [];
            helper.AllData = [];

            helper.addCellStyle = function(instance, td, row, col, prop, value, cellProperties) {
                if (cellProperties.type === 'checkbox') {
                    Handsontable.renderers.CheckboxRenderer.apply(this, arguments);
                } else if (cellProperties.type === 'dropdown') {
                    Handsontable.renderers.DropdownRenderer.apply(this, arguments);
                } else {
                    Handsontable.renderers.TextRenderer.apply(this, arguments);
                }
                if (cellProperties.type !== 'checkbox') {
                    td.style.whiteSpace = 'nowrap';
                    td.style.overflow = 'hidden';
                    td.style.textOverflow = 'ellipsis';
                }
            };

            helper.placeholderRenderer = function(instance, td, row, col, prop, value, cellProperties) {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                td.style.whiteSpace = 'nowrap';
                td.style.overflow = 'hidden';
                td.style.textOverflow = 'ellipsis';

                var children = td.children;
                if (children.length > 0) {
                    for (var i = 0; i < children.length; i++) {
                        var child = children[i];
                        child.style.whiteSpace = 'nowrap';
                        child.style.overflow = 'hidden';
                        child.style.textOverflow = 'ellipsis';
                        child.style.display = 'block';
                        child.style.maxWidth = '100%';
                    }
                }

                if (value === null || value === '') {
                    td.style.color = 'gray';
                    td.style.fontStyle = 'italic';
                    td.innerHTML = (_loginUserLanguageResource.doubleClickCellTip || 'Double click') + '...';
                }
            };

            helper.createTable = function(data) {
                $('#' + helper.divid).empty();
                var hotElement = document.querySelector('#' + helper.divid);
                helper.hot = new Handsontable(hotElement, {
                    licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                    theme: 'ht-theme-classic',
                    data: data,
                    hiddenColumns: {
                        columns: [0],
                        indicators: false,
                        copyPasteEnabled: false
                    },
                    colWidths: [50, 200, 200, 80, 80, 80, 80, 80],
                    columns: helper.columns,
                    stretchH: 'all',
                    autoWrapRow: true,
                    rowHeaders: true,
                    colHeaders: helper.colHeaders,
                    columnSorting: true,
                    sortIndicator: true,
                    manualColumnResize: true,
                    manualRowResize: true,
                    filters: true,
                    renderAllRows: true,
                    search: true,
                    outsideClickDeselects: false,
                    contextMenu: {
                        items: {
                            "row_above": {
                                name: (_loginUserLanguageResource.contextMenu_insertRowAbove || 'Insert row above')
                            },
                            "row_below": {
                                name: (_loginUserLanguageResource.contextMenu_insertRowBelow || 'Insert row below')
                            },
                            "col_left": {
                                name: (_loginUserLanguageResource.contextMenu_insertColumnLeft || 'Insert column left')
                            },
                            "col_right": {
                                name: (_loginUserLanguageResource.contextMenu_insertColumnRight || 'Insert column right')
                            },
                            "remove_row": {
                                name: (_loginUserLanguageResource.contextMenu_removeRow || 'Remove row')
                            },
                            "remove_col": {
                                name: (_loginUserLanguageResource.contextMenu_removeColumn || 'Remove column')
                            },
                            "merge_cell": {
                                name: (_loginUserLanguageResource.contextMenu_mergeCell || 'Merge cells')
                            },
                            "copy": {
                                name: (_loginUserLanguageResource.contextMenu_copy || 'Copy')
                            },
                            "cut": {
                                name: (_loginUserLanguageResource.contextMenu_cut || 'Cut')
                            }
                        }
                    },
                    cells: function(row, col, prop) {
                        var cellProperties = {};
                        if (!editFlag) {
                            cellProperties.editor = false;
                        }
                        return cellProperties;
                    },
                    afterBeginEditing: function(row, column) {
                        var cellMeta = helper.hot.getCellMeta(row, column);
                        var selectedItemName = helper.hot.getDataAtCell(row, column);
                        if (cellMeta.prop.toUpperCase() === 'title1'.toUpperCase() ||
                            cellMeta.prop.toUpperCase() === 'title2'.toUpperCase()) {

                            var tree = mini.get('protocolTree');
                            var selectedNode = tree.getSelectedNode();
                            if (!selectedNode || selectedNode.classes !== 1) {
                                return;
                            }
                            var protocolCode = selectedNode.code || '';

                            mini.open({
                                title: _loginUserLanguageResource.config,
                                url: context + '/miniui-app/modules/driverConfig/protocolExtendedFieldSelectWindow.jsp',
                                width: 600,
                                height: 800,
                                modal: true,
                                allowResize: true,
                                onload: function() {
                                    var iframe = this.getIFrameEl();
                                    var contentWindow = iframe.contentWindow;
                                    contentWindow.setData({
                                        protocolCode: protocolCode,
                                        row: row,
                                        col: column,
                                        fieldType: 1, // 1:高低字节
                                        currentValue: selectedItemName || ''
                                    });
                                    contentWindow.parent.setExtendedFieldValue = function(r, c, value) {
                                        helper.hot.setDataAtCell(r, c, value);
                                    };
                                },
                                ondestroy: function(action) {
                                    // 关闭后无额外操作
                                }
                            });
                        }
                    },
                    afterChange: function(changes, source) {
                        if (!changes) return;
                        changes.forEach(function(change) {
                            var row = change[0],
                                prop = change[1],
                                oldVal = change[2],
                                newVal = change[3];
                            if (prop === 'resolutionMode') {
                                var selectedRow = parseInt($('#ProtocolExtendedFieldHighLowByteSelectRow_Id').val() || 0);
                                if (selectedRow === row &&
                                    typeof protocolExtendedFieldMeaningConfigHandsontableHelper !== 'undefined' &&
                                    protocolExtendedFieldMeaningConfigHandsontableHelper &&
                                    protocolExtendedFieldMeaningConfigHandsontableHelper.hot) {
                                    var resolutionMode = newVal;
                                    var itemTitle = helper.hot.getDataAtRowProp(row, 'title');
                                    var protocolCode = getCurrentProtocolCode();
                                    CreateProtocolExtendedFieldMeaningConfigInfoTable(protocolCode, itemTitle, resolutionMode, true);
                                }
                            }
                        });
                    },
                    afterSelectionEnd: function(row, column, row2, column2, selectionLayerLevel) {
                        if (row < 0 && row2 < 0) {
                            // 只选中表头
                            $('#ProtocolExtendedFieldHighLowByteSelectRow_Id').val('');
                            CreateProtocolExtendedFieldMeaningConfigInfoTable('', '', '', true);
                            return;
                        }
                        var startRow = (row < 0) ? 0 : row;
                        if (row2 < 0) row2 = 0;
                        if (row > row2) {
                            startRow = row2;
                            row2 = row;
                        }
                        var selectedRow = parseInt($('#ProtocolExtendedFieldHighLowByteSelectRow_Id').val() || 0);
                        if (selectedRow !== startRow) {
                            $('#ProtocolExtendedFieldHighLowByteSelectRow_Id').val(startRow);
                            var protocolCode = getCurrentProtocolCode();
                            var itemTitle = helper.hot.getDataAtRowProp(startRow, 'title');
                            var resolutionMode = helper.hot.getDataAtRowProp(startRow, 'resolutionMode');
                            CreateProtocolExtendedFieldMeaningConfigInfoTable(protocolCode, itemTitle, resolutionMode, true);
                        }
                    },
                    afterOnCellMouseOver: function(event, coords, TD) {}
                });
            };
            return helper;
        }
    };
    
  //================================================================
    //扩展字段 - 高低字节含义表格 Helper
    //================================================================
    var ProtocolExtendedFieldMeaningConfigHandsontableHelper = {
        createNew: function(divid) {
            var helper = {};
            helper.hot = null;
            helper.divid = divid;
            helper.validresult = true;
            helper.colHeaders = [];
            helper.columns = [];
            helper.AllData = [];
            helper.itemResolutionMode = 2;
            helper.hiddenColumns = [];
            helper.contextMenu = null;

            helper.addCellStyle = function(instance, td, row, col, prop, value, cellProperties) {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                td.style.whiteSpace = 'nowrap';
                td.style.overflow = 'hidden';
                td.style.textOverflow = 'ellipsis';
            };

            helper.addBoldBg = function(instance, td, row, col, prop, value, cellProperties) {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                td.style.backgroundColor = 'rgb(245, 245, 245)';
                td.style.whiteSpace = 'nowrap';
                td.style.overflow = 'hidden';
                td.style.textOverflow = 'ellipsis';
            };

            helper.createTable = function(data) {
                $('#' + helper.divid).empty();
                var hotElement = document.querySelector('#' + helper.divid);
                helper.hot = new Handsontable(hotElement, {
                    licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                    theme: 'ht-theme-classic',
                    data: data,
                    hiddenColumns: {
                        columns: helper.hiddenColumns,
                        indicators: false,
                        copyPasteEnabled: false
                    },
                    colWidths: [1, 3],
                    columns: helper.columns,
                    stretchH: 'all',
                    autoWrapRow: true,
                    rowHeaders: false,
                    colHeaders: helper.colHeaders,
                    columnSorting: true,
                    sortIndicator: true,
                    manualColumnResize: true,
                    manualRowResize: true,
                    filters: true,
                    renderAllRows: true,
                    search: true,
                    contextMenu: helper.contextMenu,
                    cells: function(row, col, prop) {
                        var cellProperties = {};
                        if (!editFlag) {
                            cellProperties.editor = false;
                            cellProperties.renderer = helper.addCellStyle;
                        } else {
                            if (helper.itemResolutionMode === 0 && prop === 'title') {
                                cellProperties.editor = false;
                                cellProperties.renderer = helper.addBoldBg;
                            } else {
                                cellProperties.renderer = helper.addCellStyle;
                            }
                        }
                        return cellProperties;
                    },
                    afterOnCellMouseOver: function(event, coords, TD) {}
                });
            };
            return helper;
        }
    };
    
  //================================================================
    //扩展字段 - 高低字节开关量位状态表格 Helper
    //================================================================
    var ProtocolExtendedFieldSwitchingValueBitStatusConfigHandsontableHelper = {
        createNew: function(divid) {
            var helper = {};
            helper.hot = null;
            helper.divid = divid;
            helper.validresult = true;
            helper.colHeaders = [];
            helper.columns = [];
            helper.AllData = [];

            helper.addCellStyle = function(instance, td, row, col, prop, value, cellProperties) {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                td.style.whiteSpace = 'nowrap';
                td.style.overflow = 'hidden';
                td.style.textOverflow = 'ellipsis';
            };

            helper.addBoldBg = function(instance, td, row, col, prop, value, cellProperties) {
                Handsontable.renderers.TextRenderer.apply(this, arguments);
                td.style.backgroundColor = 'rgb(245, 245, 245)';
                td.style.whiteSpace = 'nowrap';
                td.style.overflow = 'hidden';
                td.style.textOverflow = 'ellipsis';
            };

            helper.createTable = function(data) {
                $('#' + helper.divid).empty();
                var hotElement = document.querySelector('#' + helper.divid);
                helper.hot = new Handsontable(hotElement, {
                    licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                    theme: 'ht-theme-classic',
                    data: data,
                    hiddenColumns: {
                        columns: [2, 3],
                        indicators: false,
                        copyPasteEnabled: false
                    },
                    colWidths: [2, 5],
                    columns: helper.columns,
                    stretchH: 'all',
                    autoWrapRow: true,
                    rowHeaders: false,
                    colHeaders: helper.colHeaders,
                    columnSorting: true,
                    sortIndicator: true,
                    manualColumnResize: true,
                    manualRowResize: true,
                    filters: true,
                    renderAllRows: true,
                    search: true,
                    cells: function(row, col, prop) {
                        var cellProperties = {};
                        if (!editFlag) {
                            cellProperties.editor = false;
                            cellProperties.renderer = helper.addCellStyle;
                        } else {
                            if (prop === 'title') {
                                cellProperties.editor = false;
                                cellProperties.renderer = helper.addBoldBg;
                            } else {
                                cellProperties.renderer = helper.addCellStyle;
                            }
                        }
                        return cellProperties;
                    },
                    afterOnCellMouseOver: function(event, coords, TD) {}
                });
            };
            return helper;
        }
    };
    
  //================================================================
  //采集单元属性表格 Helper
  //================================================================
  var ProtocolConfigAcqUnitPropertiesHandsontableHelper = {
     createNew: function (divid) {
         var helper = {};
         helper.hot = '';
         helper.classes = null;
         helper.type = null;
         helper.divid = divid;
         helper.validresult = true;
         helper.colHeaders = [];
         helper.columns = [];
         helper.AllData = [];

         helper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
             Handsontable.renderers.TextRenderer.apply(this, arguments);
             td.style.backgroundColor = 'rgb(245, 245, 245)';
             td.style.whiteSpace = 'nowrap';
             td.style.overflow = 'hidden';
             td.style.textOverflow = 'ellipsis';
         };

         helper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
             Handsontable.renderers.TextRenderer.apply(this, arguments);
             td.style.whiteSpace = 'nowrap';
             td.style.overflow = 'hidden';
             td.style.textOverflow = 'ellipsis';
         };

         helper.createTable = function (data) {
             $('#' + helper.divid).empty();
             var hotElement = document.querySelector('#' + helper.divid);
             helper.hot = new Handsontable(hotElement, {
                 licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                 theme: 'ht-theme-classic',
                 data: data,
                 colWidths: [1, 5, 5],
                 columns: helper.columns,
                 stretchH: 'all',
                 autoWrapRow: true,
                 rowHeaders: false,
                 colHeaders: helper.colHeaders,
                 columnSorting: true,
                 sortIndicator: true,
                 manualColumnResize: true,
                 manualRowResize: true,
                 filters: true,
                 renderAllRows: true,
                 search: true,
                 contextMenu: {
                     items: {
                         "copy": { name: _loginUserLanguageResource.contextMenu_copy },
                         "cut": { name: _loginUserLanguageResource.contextMenu_cut }
                     }
                 },
                 cells: function (row, col, prop) {
                     var cellProperties = {};
                     var visualRowIndex = this.instance.toVisualRow(row);
                     var visualColIndex = this.instance.toVisualColumn(col);

                     var protocolConfigModuleEditFlag = (typeof editFlag !== 'undefined' && editFlag) ? 1 : 0;
                     if (protocolConfigModuleEditFlag == 1) {
                         if (visualColIndex == 0 || visualColIndex == 1) {
                             cellProperties.editor = false;
                             cellProperties.renderer = helper.addBoldBg;
                         }
                         if (helper.classes === 0 || helper.classes === 1) {
                             cellProperties.editor = false;
                             cellProperties.renderer = helper.addBoldBg;
                         } else if (helper.classes === 2) {
                             if (visualColIndex === 2 && visualRowIndex === 0) {
                                 this.validator = function (val, callback) {
                                     return handsontableDataCheck_NotNull(val, callback, row, col, helper);
                                 };
                             } else if (visualColIndex === 2 && visualRowIndex === 1) {
                                 this.validator = function (val, callback) {
                                     return handsontableDataCheck_Num_Nullable(val, callback, row, col, helper);
                                 };
                             }
                             if (visualColIndex === 2) {
                                 cellProperties.renderer = helper.addCellStyle;
                             }
                         } else if (helper.classes === 3) {
                             if (visualColIndex === 2 && visualRowIndex === 0) {
                                 this.validator = function (val, callback) {
                                     return handsontableDataCheck_NotNull(val, callback, row, col, helper);
                                 };
                             } else if (visualColIndex === 2 && visualRowIndex === 1) {
                                 this.type = 'dropdown';
                                 this.source = [_loginUserLanguageResource.acqGroup, _loginUserLanguageResource.controlGroup];
                                 this.strict = true;
                                 this.allowInvalid = false;
                             } else if (visualColIndex === 2 && (visualRowIndex === 2 || visualRowIndex === 3) && helper.type == 0) {
                                 this.validator = function (val, callback) {
                                     return handsontableDataCheck_Num_Nullable(val, callback, row, col, helper);
                                 };
                                 cellProperties.renderer = helper.addCellStyle;
                             }
                         }
                     } else {
                         cellProperties.editor = false;
                         cellProperties.renderer = helper.addBoldBg;
                     }
                     return cellProperties;
                 },
                 afterOnCellMouseOver: function (event, coords, TD) {
                 }
             });
         };

         helper.saveData = function () {};
         helper.clearContainer = function () {
             helper.AllData = [];
         };
         return helper;
     }
  };

  //================================================================
  //采集单元配置表格 Helper
  //================================================================
  var ProtocolAcqUnitConfigItemsHandsontableHelper = {
     createNew: function (divid) {
         var helper = {};
         helper.hot = '';
         helper.divid = divid;
         helper.validresult = true;
         helper.colHeaders = [];
         helper.columns = [];
         helper.AllData = [];
         helper.hiddenColumns = [];
         helper.colWidths = [];

         helper.addCellStyle = function (instance, td, row, col, prop, value, cellProperties) {
             Handsontable.renderers.TextRenderer.apply(this, arguments);
             td.style.whiteSpace = 'nowrap';
             td.style.overflow = 'hidden';
             td.style.textOverflow = 'ellipsis';
         };

         helper.addBoldBg = function (instance, td, row, col, prop, value, cellProperties) {
             Handsontable.renderers.TextRenderer.apply(this, arguments);
             td.style.backgroundColor = 'rgb(245, 245, 245)';
         };

         helper.addReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
             if (cellProperties.type == 'checkbox') {
                 helper.addCheckboxReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
             } else if (cellProperties.type == 'dropdown') {
                 helper.addDropdownReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
             } else {
                 helper.addTextReadOnlyBg(instance, td, row, col, prop, value, cellProperties);
             }
         };

         helper.addCheckboxReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
             Handsontable.renderers.CheckboxRenderer.apply(this, arguments);
             td.style.backgroundColor = 'rgb(245, 245, 245)';
         };

         helper.addDropdownReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
             Handsontable.renderers.DropdownRenderer.apply(this, arguments);
             td.style.backgroundColor = 'rgb(245, 245, 245)';
             td.style.whiteSpace = 'nowrap';
             td.style.overflow = 'hidden';
             td.style.textOverflow = 'ellipsis';
         };

         helper.addTextReadOnlyBg = function (instance, td, row, col, prop, value, cellProperties) {
             Handsontable.renderers.TextRenderer.apply(this, arguments);
             td.style.backgroundColor = 'rgb(245, 245, 245)';
             td.style.whiteSpace = 'nowrap';
             td.style.overflow = 'hidden';
             td.style.textOverflow = 'ellipsis';
         };

         helper.createTable = function (data) {
             $('#' + helper.divid).empty();
             var hotElement = document.querySelector('#' + helper.divid);
             helper.hot = new Handsontable(hotElement, {
                 licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
                 theme: 'ht-theme-classic',
                 data: data,
                 hiddenColumns: {
                     columns: helper.hiddenColumns,
                     indicators: false,
                     copyPasteEnabled: false
                 },
                 colWidths: helper.colWidths,
                 columns: helper.columns,
                 stretchH: 'all',
                 width: '100%',          // 水平方向 100%
                 height: '100%',         // ★ 关键：垂直方向 100%，由父容器决定
                 autoWrapRow: true,
                 rowHeaders: false,
                 colHeaders: helper.colHeaders,
                 columnSorting: true,
                 sortIndicator: true,
                 manualColumnResize: true,
                 manualRowResize: true,
                 filters: true,
                 renderAllRows: true,
                 search: true,
                 contextMenu: {
                     items: {
                         "copy": { name: _loginUserLanguageResource.contextMenu_copy || '复制' },
                         "cut": { name: _loginUserLanguageResource.contextMenu_cut || '剪切' }
                     }
                 },
                 cells: function (row, col, prop) {
                     var cellProperties = {};
                     var visualRowIndex = this.instance.toVisualRow(row);
                     var visualColIndex = this.instance.toVisualColumn(col);

                     var protocolConfigModuleEditFlag = (typeof editFlag !== 'undefined' && editFlag) ? 1 : 0;
                     if (protocolConfigModuleEditFlag == 1) {
                         var selectedItem = _currentAcqUnitNode; // 当前选中的节点
                         if (selectedItem && selectedItem.classes !== 3) {
                             cellProperties.editor = false;
                             cellProperties.renderer = helper.addReadOnlyBg;
                         } else {
                             if (visualColIndex >= 1 && visualColIndex <= 6) {
                                 cellProperties.editor = false;
                                 cellProperties.renderer = helper.addReadOnlyBg;
                             } else if (visualColIndex != 10 && visualColIndex != 12) {
                                 if (helper.columns[visualColIndex].type != 'dropdown' && helper.columns[visualColIndex].type != 'checkbox') {
                                     cellProperties.renderer = helper.addCellStyle;
                                 }
                             }
                         }
                     } else {
                         cellProperties.editor = false;
                         cellProperties.renderer = helper.addReadOnlyBg;
                     }
                     return cellProperties;
                 },
                 beforeChange: function (changes, source) {
                     if (!changes) return true;
                     var protocolConfigModuleEditFlag = (typeof editFlag !== 'undefined' && editFlag) ? 1 : 0;
                     if (protocolConfigModuleEditFlag === 0) {
                         return false;
                     }
                     return true;
                 },
                 afterOnCellMouseOver: function (event, coords, TD) {
                 }
             });
         };

         helper.saveData = function () {};
         helper.clearContainer = function () {
             helper.AllData = [];
         };
         return helper;
     }
  };
  
  var ProtocolDisplayUnitPropertiesHandsontableHelper = {
		    createNew: function(divid) {
		        var helper = {};
		        helper.hot = null;
		        helper.classes = null;
		        helper.divid = divid;
		        helper.validresult = true;
		        helper.colHeaders = [];
		        helper.columns = [];
		        helper.AllData = [];
		        helper.unitList = [];
		        helper.unitIdNameList = [];

		        helper.addBoldBg = function(instance, td, row, col, prop, value, cellProperties) {
		            Handsontable.renderers.TextRenderer.apply(this, arguments);
		            td.style.backgroundColor = 'rgb(245, 245, 245)';
		            td.style.whiteSpace = 'nowrap';
		            td.style.overflow = 'hidden';
		            td.style.textOverflow = 'ellipsis';
		        };

		        helper.addCellStyle = function(instance, td, row, col, prop, value, cellProperties) {
		            Handsontable.renderers.TextRenderer.apply(this, arguments);
		            td.style.whiteSpace = 'nowrap';
		            td.style.overflow = 'hidden';
		            td.style.textOverflow = 'ellipsis';
		        };

		        helper.createTable = function(data) {
		            $('#' + helper.divid).empty();
		            var hotElement = document.querySelector('#' + helper.divid);
		            helper.hot = new Handsontable(hotElement, {
		                licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
		                theme: 'ht-theme-classic',
		                data: data,
		                colWidths: [1, 4, 5],
		                columns: helper.columns,
		                stretchH: 'all',
		                width: '100%',          // 水平方向 100%
		                height: '100%',         // ★ 关键：垂直方向 100%，由父容器决定
		                autoWrapRow: true,
		                rowHeaders: false,
		                colHeaders: helper.colHeaders,
		                columnSorting: true,
		                sortIndicator: true,
		                manualColumnResize: true,
		                manualRowResize: true,
		                filters: true,
		                renderAllRows: true,
		                search: true,
		                contextMenu: {
		                    items: {
		                        "copy": {
		                            name: _loginUserLanguageResource.contextMenu_copy
		                        },
		                        "cut": {
		                            name: _loginUserLanguageResource.contextMenu_cut
		                        }
		                    }
		                },
		                cells: function(row, col, prop) {
		                    var cellProperties = {};
		                    var visualRowIndex = this.instance.toVisualRow(row);
		                    var visualColIndex = this.instance.toVisualColumn(col);

		                    // 权限控制
		                    if (!editFlag) {
		                        cellProperties.editor = false;
		                        cellProperties.renderer = helper.addBoldBg;
		                        return cellProperties;
		                    }

		                    // 根据 classes 控制编辑和渲染
		                    if (helper.classes === 0 || helper.classes === 1) {
		                        cellProperties.editor = false;
		                        cellProperties.renderer = helper.addBoldBg;
		                    } else if (helper.classes === 2) {
		                        if (visualColIndex === 0 || visualColIndex === 1) {
		                            cellProperties.editor = false;
		                            cellProperties.renderer = helper.addBoldBg;
		                        } else {
		                            // 第3列（value列）
		                            if (visualColIndex === 2 && visualRowIndex === 0) {
		                                // 单元名：非空校验
		                                this.validator = function(val, callback) {
		                                    return handsontableDataCheck_NotNull(val, callback, row, col, helper);
		                                };
		                                cellProperties.renderer = helper.addCellStyle;
		                            } else if (visualColIndex === 2 && visualRowIndex === 1) {
		                                // 关联采集单元：下拉框
		                                this.type = 'dropdown';
		                                this.strict = true;
		                                this.allowInvalid = false;
		                                this.source = helper.unitList;
		                            } else if (visualColIndex === 2 && visualRowIndex === 2) {
		                                // 计算类型：下拉框
		                                this.type = 'dropdown';
		                                this.strict = true;
		                                this.allowInvalid = false;
		                                this.source = [_loginUserLanguageResource.nothing, _loginUserLanguageResource.SRPCalculate, _loginUserLanguageResource.PCPCalculate];
		                            } else if (visualColIndex === 2 && visualRowIndex === 3) {
		                                // 序号：可空数字校验
		                                this.validator = function(val, callback) {
		                                    return handsontableDataCheck_Num_Nullable(val, callback, row, col, helper);
		                                };
		                                cellProperties.renderer = helper.addCellStyle;
		                            } else {
		                                cellProperties.renderer = helper.addCellStyle;
		                            }
		                        }
		                    }
		                    return cellProperties;
		                },
		                afterOnCellMouseOver: function(event, coords, TD) {
		                	
		                }
		            });
		        };

		        helper.saveData = function() {};
		        helper.clearContainer = function() {
		            helper.AllData = [];
		        };
		        return helper;
		    }
		};

//================================================================
//采集项配置 Handsontable Helper
//================================================================
var ProtocolDisplayUnitAcqItemsConfigHandsontableHelper = {
   createNew: function(divid) {
       var helper = {};
       helper.hot = null;
       helper.divid = divid;
       helper.validresult = true;
       helper.colHeaders = [];    // 用于 nestedHeaders
       helper.columns = [];
       helper.AllData = [];
       helper.hiddenColumns = [];
       helper.colWidths = [];

       // ---- 自定义渲染器 ----
       // 曲线背景（可编辑）
       helper.addCurveBg = function(instance, td, row, col, prop, value, cellProperties) {
           Handsontable.renderers.TextRenderer.apply(this, arguments);
           if (value != null && value !== '') {
               var arr = (value + '').split(';');
               if (arr.length === 4) {
                   td.style.backgroundColor = '#' + arr[3];
               }
           }
           td.style.whiteSpace = 'nowrap';
           td.style.overflow = 'hidden';
           td.style.textOverflow = 'ellipsis';
       };

       // 曲线背景（只读）
       helper.addReadOnlyCurveBg = function(instance, td, row, col, prop, value, cellProperties) {
           Handsontable.renderers.TextRenderer.apply(this, arguments);
           var bg = 'rgb(245, 245, 245)';
           if (value != null && value !== '') {
               var arr = (value + '').split(';');
               if (arr.length === 4) {
                   bg = '#' + arr[3];
               }
           }
           td.style.backgroundColor = bg;
           td.style.whiteSpace = 'nowrap';
           td.style.overflow = 'hidden';
           td.style.textOverflow = 'ellipsis';
       };

       // 颜色单元格（可编辑）
       helper.addCellBgColor = function(instance, td, row, col, prop, value, cellProperties) {
           Handsontable.renderers.TextRenderer.apply(this, arguments);
           if (value != null && value !== '') {
               td.style.backgroundColor = '#' + value;
           }
           td.style.whiteSpace = 'nowrap';
           td.style.overflow = 'hidden';
           td.style.textOverflow = 'ellipsis';
       };

       // 颜色单元格（只读）
       helper.addReadOnlyCellBgColor = function(instance, td, row, col, prop, value, cellProperties) {
           Handsontable.renderers.TextRenderer.apply(this, arguments);
           if (value != null && value !== '') {
               td.style.backgroundColor = '#' + value;
           } else {
               td.style.backgroundColor = 'rgb(245, 245, 245)';
           }
           td.style.whiteSpace = 'nowrap';
           td.style.overflow = 'hidden';
           td.style.textOverflow = 'ellipsis';
       };

       // 普通文本样式
       helper.addCellStyle = function(instance, td, row, col, prop, value, cellProperties) {
           Handsontable.renderers.TextRenderer.apply(this, arguments);
           td.style.whiteSpace = 'nowrap';
           td.style.overflow = 'hidden';
           td.style.textOverflow = 'ellipsis';
       };

       // ---- 只读背景（统一入口） ----
       helper.addReadOnlyBg = function(instance, td, row, col, prop, value, cellProperties) {
           if (cellProperties.type === 'checkbox') {
               Handsontable.renderers.CheckboxRenderer.apply(this, arguments);
               td.style.backgroundColor = 'rgb(245, 245, 245)';
           } else if (cellProperties.type === 'dropdown') {
               Handsontable.renderers.DropdownRenderer.apply(this, arguments);
               td.style.backgroundColor = 'rgb(245, 245, 245)';
               td.style.whiteSpace = 'nowrap';
               td.style.overflow = 'hidden';
               td.style.textOverflow = 'ellipsis';
           } else {
               Handsontable.renderers.TextRenderer.apply(this, arguments);
               td.style.backgroundColor = 'rgb(245, 245, 245)';
               td.style.whiteSpace = 'nowrap';
               td.style.overflow = 'hidden';
               td.style.textOverflow = 'ellipsis';
           }
       };

       // ---- 创建表格 ----
       helper.createTable = function(data) {
           $('#' + helper.divid).empty();
           var hotElement = document.querySelector('#' + helper.divid);

           // 获取父容器实际宽高（让表格撑满）
           var container = document.getElementById(helper.divid);

           helper.hot = new Handsontable(hotElement, {
               licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
               theme: 'ht-theme-classic',
               data: data,
               hiddenColumns: {
                   columns: helper.hiddenColumns,
                   indicators: false,
                   copyPasteEnabled: false
               },
               colWidths: helper.colWidths,
               columns: helper.columns,
               fixedColumnsStart: 3,
               stretchH: 'all',
               width: '100%', 
               height: '100%',
               autoWrapRow: true,
               rowHeaders: false,
               nestedHeaders: helper.colHeaders,
               columnSorting: true,
               sortIndicator: true,
               manualColumnResize: true,
               manualRowResize: true,
               filters: true,
               renderAllRows: true,
               search: true,
               contextMenu: {
                   items: {
                       "copy": { name: _loginUserLanguageResource.contextMenu_copy },
                       "cut": { name: _loginUserLanguageResource.contextMenu_cut }
                   }
               },
               cells: function(row, col, prop) {
                   var cellProperties = {};
                   var visualRowIndex = this.instance.toVisualRow(row);
                   var visualColIndex = this.instance.toVisualColumn(col);

                   // 权限控制
                   if (!editFlag) {
                       cellProperties.editor = false;
                       // 对特定列使用只读渲染器
                       if (visualColIndex === 12 || visualColIndex === 19) {
                           cellProperties.renderer = helper.addReadOnlyCurveBg;
                       } else if (visualColIndex === 10 || visualColIndex === 11 || visualColIndex === 17 || visualColIndex === 18) {
                           cellProperties.renderer = helper.addReadOnlyCellBgColor;
                       } else {
                           cellProperties.renderer = helper.addReadOnlyBg;
                       }
                       return cellProperties;
                   }

                   // 有编辑权限时
                   // 根据当前选中的节点类型（classes）判断是否可编辑（仅在 classes===2 时可编辑）
                   // 由于当前函数无法直接获取选中节点，我们通过全局变量 _currentDisplayUnitNode 判断
                   var isUnit = (_currentDisplayUnitNode && _currentDisplayUnitNode.classes === 2);
                   if (!isUnit) {
                       cellProperties.editor = false;
                       // 同样应用只读渲染器
                       if (visualColIndex === 12 || visualColIndex === 19) {
                           cellProperties.renderer = helper.addReadOnlyCurveBg;
                       } else if (visualColIndex === 10 || visualColIndex === 11 || visualColIndex === 17 || visualColIndex === 18) {
                           cellProperties.renderer = helper.addReadOnlyCellBgColor;
                       } else {
                           cellProperties.renderer = helper.addReadOnlyBg;
                       }
                       return cellProperties;
                   }

                   // 单元节点（classes===2）下的编辑规则
                   if (prop === 'id' || prop === 'showTitle' || prop === 'dataSource' || prop === 'unit') {
                       cellProperties.editor = false;
                       cellProperties.renderer = helper.addReadOnlyBg;
                   } else if (prop === 'realtimeCurveConfShowValue' || prop === 'historyCurveConfShowValue') {
                       // 曲线显示值
                       cellProperties.renderer = helper.addCurveBg;
                   } else if (prop === 'realtimeColor' || prop === 'realtimeBgColor' || prop === 'historyColor' || prop === 'historyBgColor') {
                       cellProperties.renderer = helper.addCellBgColor;
                   } else if (prop === 'switchingValueShowType') {
                       var resolutionMode = this.instance.getDataAtRowProp(row, 'resolutionMode');
                       if (resolutionMode !== _loginUserLanguageResource.switchingValue) {
                           cellProperties.editor = false;
                           cellProperties.renderer = helper.addReadOnlyBg;
                       } else {
                           cellProperties.renderer = helper.addCellStyle;
                       }
                   } else {
                       if (helper.columns[visualColIndex] && helper.columns[visualColIndex].type !== 'dropdown' &&
                           helper.columns[visualColIndex].type !== 'checkbox') {
                           cellProperties.renderer = helper.addCellStyle;
                       }
                   }
                   return cellProperties;
               },
               beforeChange: function(changes, source) {
                   if (!changes) return true;
                   if (!editFlag) return false;
                   // 可在此添加额外校验
                   return true;
               },
               afterBeginEditing: function (row, column) {
            	    if (!editFlag) return;

            	    var helper = protocolDisplayUnitAcqItemsConfigHandsontableHelper;
            	    if (!helper || !helper.hot) return;

            	    var rowData = helper.hot.getDataAtRow(row);
            	    var realtimeData = rowData[8];  // 索引8: realtimeData
            	    var historyData = rowData[15];  // 索引15: historyData

            	    // 曲线配置列 (12:实时曲线显示值, 19:历史曲线显示值)
            	    if ((realtimeData && column === 12) || (historyData && column === 19)) {
            	        if (_currentDisplayUnitNode && _currentDisplayUnitNode.classes === 2) {
            	            openCurveConfigWindow(row, column, 0); // 0:采集项表
            	        }
            	        return;
            	    }

            	    // 颜色列 (10:实时前景色, 11:实时背景色, 17:历史前景色, 18:历史背景色)
            	    if ((realtimeData && (column === 10 || column === 11)) ||
            	        (historyData && (column === 17 || column === 18))) {
            	        if (_currentDisplayUnitNode && _currentDisplayUnitNode.classes === 2) {
            	            openColorPickerWindow(row, column, 0);
            	        }
            	        return;
            	    }
            	},
            	afterChange: function (changes, source) {
            	    if (!changes) return;
            	    var helper = protocolDisplayUnitAcqItemsConfigHandsontableHelper;
            	    if (!helper || !helper.hot) return;

            	    changes.forEach(([row, prop, oldValue, newValue]) => {
            	        if (source === 'CopyPaste.paste' && (prop === 'realtimeCurveConfShowValue' || prop === 'historyCurveConfShowValue')) {
            	            var configCol = (prop === 'realtimeCurveConfShowValue') ? 21 : 22;
            	            if (newValue && newValue.split(';').length === 4) {
            	                var arr = newValue.split(';');
            	                var groupName = arr[0].replace(_loginUserLanguageResource.curveGroup + ':', '');
            	                var sort = parseInt(arr[1]) || 0;
            	                var yAxisOpposite = arr[2] === _loginUserLanguageResource.right;
            	                var color = arr[3];
            	                var config = {
            	                    groupId: -1,
            	                    groupName: groupName,
            	                    sort: sort,
            	                    lineWidth: 3,
            	                    dashStyle: 'Solid',
            	                    yAxisOpposite: yAxisOpposite,
            	                    color: color
            	                };
            	                helper.hot.setDataAtCell(row, configCol, config);
            	            } else {
            	                helper.hot.setDataAtCell(row, configCol, '');
            	            }
            	        }
            	    });
            	},
               afterOnCellMouseOver: function(event, coords, TD) {
                   if (coords.col >= 0 && coords.row >= 0 &&
                       helper.columns[coords.col] && helper.columns[coords.col].type !== 'checkbox' &&
                       helper.hot && helper.hot.getDataAtCell) {
                       var rawValue = helper.hot.getDataAtCell(coords.row, coords.col);
                       if (rawValue && rawValue.length > 0) {
                           TD.title = rawValue;
                       }
                   }
               }
           });
       };

       helper.saveData = function() {};
       helper.clearContainer = function() {
           helper.AllData = [];
       };
       return helper;
   }
};

//================================================================
//控制项配置 Handsontable Helper
//================================================================
var ProtocolDisplayUnitCtrlItemsConfigHandsontableHelper = {
 createNew: function(divid) {
     var helper = {};
     helper.hot = null;
     helper.divid = divid;
     helper.validresult = true;
     helper.colHeaders = [];
     helper.columns = [];
     helper.AllData = [];
     helper.hiddenColumns = [];
     helper.colWidths = [];

     helper.addCellStyle = function(instance, td, row, col, prop, value, cellProperties) {
         Handsontable.renderers.TextRenderer.apply(this, arguments);
         td.style.whiteSpace = 'nowrap';
         td.style.overflow = 'hidden';
         td.style.textOverflow = 'ellipsis';
     };

     helper.addReadOnlyBg = function(instance, td, row, col, prop, value, cellProperties) {
         if (cellProperties.type === 'checkbox') {
             Handsontable.renderers.CheckboxRenderer.apply(this, arguments);
             td.style.backgroundColor = 'rgb(245, 245, 245)';
         } else if (cellProperties.type === 'dropdown') {
             Handsontable.renderers.DropdownRenderer.apply(this, arguments);
             td.style.backgroundColor = 'rgb(245, 245, 245)';
             td.style.whiteSpace = 'nowrap';
             td.style.overflow = 'hidden';
             td.style.textOverflow = 'ellipsis';
         } else {
             Handsontable.renderers.TextRenderer.apply(this, arguments);
             td.style.backgroundColor = 'rgb(245, 245, 245)';
             td.style.whiteSpace = 'nowrap';
             td.style.overflow = 'hidden';
             td.style.textOverflow = 'ellipsis';
         }
     };

     helper.createTable = function(data) {
         $('#' + helper.divid).empty();
         var hotElement = document.querySelector('#' + helper.divid);
         var container = document.getElementById(helper.divid);

         helper.hot = new Handsontable(hotElement, {
             licenseKey: '96860-f3be6-b4941-2bd32-fd62b',
             theme: 'ht-theme-classic',
             data: data,
             hiddenColumns: {
                 columns: helper.hiddenColumns,
                 indicators: false,
                 copyPasteEnabled: false
             },
             colWidths: helper.colWidths,
             columns: helper.columns,
             stretchH: 'all',
             width: '100%',
             height: '100%',
             autoWrapRow: true,
             rowHeaders: false,
             colHeaders: helper.colHeaders,
             columnSorting: true,
             sortIndicator: true,
             manualColumnResize: true,
             manualRowResize: true,
             filters: true,
             renderAllRows: true,
             search: true,
             contextMenu: {
                 items: {
                     "copy": { name: _loginUserLanguageResource.contextMenu_copy },
                     "cut": { name: _loginUserLanguageResource.contextMenu_cut }
                 }
             },
             cells: function(row, col, prop) {
                 var cellProperties = {};
                 var visualRowIndex = this.instance.toVisualRow(row);
                 var visualColIndex = this.instance.toVisualColumn(col);

                 if (!editFlag) {
                     cellProperties.editor = false;
                     cellProperties.renderer = helper.addReadOnlyBg;
                     return cellProperties;
                 }

                 var isUnit = (_currentDisplayUnitNode && _currentDisplayUnitNode.classes === 2);
                 if (!isUnit) {
                     cellProperties.editor = false;
                     cellProperties.renderer = helper.addReadOnlyBg;
                     return cellProperties;
                 }

                 // 单元节点下的编辑规则
                 if (visualColIndex >= 1 && visualColIndex <= 3) {
                     cellProperties.editor = false;
                     cellProperties.renderer = helper.addReadOnlyBg;
                 } else if (visualColIndex === 6) {
                     // switchingValueShowType 列，仅当 resolutionMode 为开关量时可编辑
                     var resolutionMode = this.instance.getDataAtRowProp(row, 'resolutionMode');
                     if (resolutionMode !== _loginUserLanguageResource.switchingValue) {
                         cellProperties.editor = false;
                         cellProperties.renderer = helper.addReadOnlyBg;
                     } else {
                         cellProperties.renderer = helper.addCellStyle;
                     }
                 } else {
                     // 其他可编辑列
                     if (helper.columns[visualColIndex] && helper.columns[visualColIndex].type !== 'dropdown' &&
                         helper.columns[visualColIndex].type !== 'checkbox') {
                         cellProperties.renderer = helper.addCellStyle;
                     }
                 }
                 return cellProperties;
             },
             beforeChange: function(changes, source) {
                 if (!changes) return true;
                 if (!editFlag) return false;
                 return true;
             },
             afterOnCellMouseOver: function(event, coords, TD) {
                 if (coords.col >= 0 && coords.row >= 0 &&
                     helper.columns[coords.col] && helper.columns[coords.col].type !== 'checkbox' &&
                     helper.hot && helper.hot.getDataAtCell) {
                     var rawValue = helper.hot.getDataAtCell(coords.row, coords.col);
                     if (rawValue && rawValue.length > 0) {
                         TD.title = rawValue;
                     }
                 }
             }
         });
     };

     helper.saveData = function() {};
     helper.clearContainer = function() {
         helper.AllData = [];
     };
     return helper;
 }
};
		
		