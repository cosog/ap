Ext.define(
				"Ext.ux.form.ComboBoxCancel",
				{
					extend : "Ext.form.field.Picker",
					requires : [ "Ext.util.DelayedTask", "Ext.EventObject",
							"Ext.view.BoundList", "Ext.view.BoundListKeyNav",
							"Ext.data.StoreManager" ],
					alternateClassName : "Ext.form.ComboBoxCancel",
					alias : [ "widget.comboboxcancel", "widget.combocancel" ],
					triggerCls : Ext.baseCSSPrefix + "form-arrow-trigger",
					trigger1Cls : Ext.baseCSSPrefix + "form-clear-trigger",
					trigger2Cls : Ext.baseCSSPrefix + "form-arrow-trigger",
					hiddenDataCls : Ext.baseCSSPrefix + "hide-display "
							+ Ext.baseCSSPrefix + "form-data-hidden",
					fieldSubTpl : [
							'<div class="{hiddenDataCls}" role="presentation"></div>',
							'<input id="{id}" type="{type}" ',
							'<tpl if="size">size="{size}" </tpl>',
							'<tpl if="tabIdx">tabIndex="{tabIdx}" </tpl>',
							'class="{fieldCls} {typeCls}" autocomplete="off" />',
							'<div id="{cmpId}-triggerWrap" class="{triggerWrapCls}" role="presentation">',
							"{triggerEl}",
							'<div class="{clearCls}" role="presentation"></div>',
							"</div>", {
								compiled : true,
								disableFormats : true
							} ],
					getSubTplData : function() {
						var A = this;
						Ext.applyIf(A.subTplData, {
							hiddenDataCls : A.hiddenDataCls
						});
						return A.callParent(arguments)
					},
					afterRender : function() {
						var A = this;
						A.callParent(arguments);
						A.setHiddenValue(A.value)
					},
					multiSelect : false,
					delimiter : ", ",
					displayField : "text",
					triggerAction : "all",
					allQuery : "",
					queryParam : "query",
					queryMode : "remote",
					queryCaching : true,
					pageSize : 0,
					autoSelect : true,
					typeAhead : false,
					typeAheadDelay : 250,
					selectOnTab : true,
					forceSelection : false,
					defaultListConfig : {
						emptyText : "",
						loadingText : "加载中...",
						loadingHeight : 70,
						minWidth : 70,
						maxHeight : 300,
						shadow : "sides"
					},
					ignoreSelection : 0,
					initComponent : function() {
						var F = this, D = Ext.isDefined, A = F.store, B = F.transform, C, E;
						Ext.applyIf(F.renderSelectors, {
							hiddenDataEl : "."
									+ F.hiddenDataCls.split(" ").join(".")
						});
						if (F.typeAhead && F.multiSelect) {
							Ext.Error
									.raise("typeAhead and multiSelect are mutually exclusive options -- please remove one of them.")
						}
						if (F.typeAhead && !F.editable) {
							Ext.Error
									.raise("If typeAhead is enabled the combo must be editable: true -- please change one of those settings.")
						}
						if (F.selectOnFocus && !F.editable) {
							Ext.Error
									.raise("If selectOnFocus is enabled the combo must be editable: true -- please change one of those settings.")
						}
						this.addEvents("select", "beforedeselect");
						if (B) {
							C = Ext.getDom(B);
							if (C) {
								A = Ext.Array.map(Ext.Array.from(C.options),
										function(G) {
											return [ G.value, G.text ]
										});
								if (!F.name) {
									F.name = C.name
								}
								if (!("value" in F)) {
									F.value = C.value
								}
							}
						}
						F.bindStore(A || "ext-empty-store", true);
						A = F.store;
						if (A.autoCreated) {
							F.queryMode = "local";
							F.valueField = F.displayField = "field1";
							if (!A.expanded) {
								F.displayField = "field2"
							}
						}
						if (!D(F.valueField)) {
							F.valueField = F.displayField
						}
						E = F.queryMode === "local";
						if (!D(F.queryDelay)) {
							F.queryDelay = E ? 10 : 500
						}
						if (!D(F.minChars)) {
							F.minChars = E ? 0 : 4
						}
						if (!F.displayTpl) {
							F.displayTpl = Ext.create("Ext.XTemplate",
									'<tpl for=".">{[typeof values === "string" ? values : values["'
											+ F.displayField
											+ '"]]}<tpl if="xindex < xcount">'
											+ F.delimiter + "</tpl></tpl>")
						} else {
							if (Ext.isString(F.displayTpl)) {
								F.displayTpl = Ext.create("Ext.XTemplate",
										F.displayTpl)
							}
						}
						F.callParent();
						F.doQueryTask = Ext.create("Ext.util.DelayedTask",
								F.doRawQuery, F);
						if (F.store.getCount() > 0) {
							F.setValue(F.value)
						}
						if (C) {
							F.render(C.parentNode, C);
							Ext.removeNode(C);
							delete F.renderTo
						}
					},
					getStore : function() {
						return this.store
					},
					beforeBlur : function() {
						this.doQueryTask.cancel();
						this.assertValue()
					},
					assertValue : function() {
						var C = this, A = C.getRawValue(), B;
						if (C.forceSelection) {
							if (C.multiSelect) {
								if (A !== C.getDisplayValue()) {
									C.setValue(C.lastSelection)
								}
							} else {
								B = C.findRecordByDisplay(A);
								if (B) {
									C.select(B)
								} else {
									C.setValue(C.lastSelection)
								}
							}
						}
						C.collapse()
					},
					onTypeAhead : function() {
						var G = this, F = G.displayField, D = G.store
								.findRecord(F, G.getRawValue()), E = G
								.getPicker(), A, C, B;
						if (D) {
							A = D.get(F);
							C = A.length;
							B = G.getRawValue().length;
							E.highlightItem(E.getNode(D));
							if (B !== 0 && B !== C) {
								G.setRawValue(A);
								G.selectText(B, A.length)
							}
						}
					},
					resetToDefault : function() {
					},
					bindStore : function(B, C) {
						var D = this, A = D.store;
						if (A && !C) {
							if (A !== B && A.autoDestroy) {
								A.destroyStore()
							} else {
								A.un({
									scope : D,
									load : D.onLoad,
									exception : D.collapse
								})
							}
							if (!B) {
								D.store = null;
								if (D.picker) {
									D.picker.bindStore(null)
								}
							}
						}
						if (B) {
							if (!C) {
								D.resetToDefault()
							}
							D.store = Ext.data.StoreManager.lookup(B);
							D.store.on({
								scope : D,
								load : D.onLoad,
								exception : D.collapse
							});
							if (D.picker) {
								D.picker.bindStore(B)
							}
						}
					},
					onLoad : function() {
						var D = this, A = D.value;
						var B = D.cancel;
						if (D.rawQuery) {
							D.rawQuery = false;
							D.syncSelection();
							if (D.picker
									&& !D.picker.getSelectionModel()
											.hasSelection()) {
								D.doAutoSelect()
							}
						} else {
							if (D.value) {
								D.setValue(D.value)
							} else {
								if (D.store.getCount()) {
									D.doAutoSelect()
								} else {
									D.setValue("")
								}
							}
						}
						if (B == "auto") {
							var C = D.triggerEl;
							C.elements[0].setDisplayed("none")
						}
					},
					doRawQuery : function() {
						this.doQuery(this.getRawValue(), false, true)
					},
					doQuery : function(G, C, D) {
						G = G || "";
						var A = this, F = {
							query : G,
							forceAll : C,
							combo : A,
							cancel : false
						}, B = A.store, E = A.queryMode === "local";
						if (A.fireEvent("beforequery", F) === false || F.cancel) {
							return false
						}
						G = F.query;
						C = F.forceAll;
						if (C || (G.length >= A.minChars)) {
							A.expand();
							if (!A.queryCaching || A.lastQuery !== G) {
								A.lastQuery = G;
								if (E) {
									if (C) {
										B.clearFilter()
									} else {
										B.clearFilter(true);
										B.filter(A.displayField, G)
									}
								} else {
									A.rawQuery = D;
									if (A.pageSize) {
										A.loadPage(1)
									} else {
										B.load({
											params : A.getParams(G)
										})
									}
								}
							}
							if (A.getRawValue() !== A.getDisplayValue()) {
								A.ignoreSelection++;
								A.picker.getSelectionModel().deselectAll();
								A.ignoreSelection--
							}
							if (E) {
								A.doAutoSelect()
							}
							if (A.typeAhead) {
								A.doTypeAhead()
							}
						}
						return true
					},
					loadPage : function(A) {
						this.store.loadPage(A, {
							params : this.getParams(this.lastQuery)
						})
					},
					onPageChange : function(A, B) {
						this.loadPage(B);
						return false
					},
					getParams : function(C) {
						var A = {}, B = this.queryParam;
						if (B) {
							A[B] = C
						}
						return A
					},
					doAutoSelect : function() {
						var D = this, C = D.picker, A, B;
						if (C && D.autoSelect && D.store.getCount() > 0) {
							A = C.getSelectionModel().lastSelected;
							B = C.getNode(A || 0);
							if (B) {
								C.highlightItem(B);
								C.listEl.scrollChildIntoView(B, false)
							}
						}
					},
					doTypeAhead : function() {
						if (!this.typeAheadTask) {
							this.typeAheadTask = Ext.create(
									"Ext.util.DelayedTask", this.onTypeAhead,
									this)
						}
						if (this.lastKey != Ext.EventObject.BACKSPACE
								&& this.lastKey != Ext.EventObject.DELETE) {
							this.typeAheadTask.delay(this.typeAheadDelay)
						}
					},
					onTriggerClick : function() {
						var A = this;
						if (!A.readOnly && !A.disabled) {
							if (A.isExpanded) {
								A.collapse()
							} else {
								A.onFocus({});
								if (A.triggerAction === "all") {
									A.doQuery(A.allQuery, true)
								} else {
									A.doQuery(A.getRawValue(), false, true)
								}
							}
							A.inputEl.focus()
						}
					},
					onKeyUp : function(C, A) {
						var D = this, B = C.getKey();
						if (!D.readOnly && !D.disabled && D.editable) {
							D.lastKey = B;
							if (!C.isSpecialKey() || B == C.BACKSPACE
									|| B == C.DELETE) {
								D.doQueryTask.delay(D.queryDelay)
							}
						}
						if (D.enableKeyEvents) {
							D.callParent(arguments)
						}
					},
					initEvents : function() {
						var A = this;
						A.callParent();
						if (!A.enableKeyEvents) {
							A.mon(A.inputEl, "keyup", A.onKeyUp, A)
						}
					},
					onDestroy : function() {
						this.bindStore(null);
						this.callParent()
					},
					createPicker : function() {
						var D = this, C, B = Ext.baseCSSPrefix + "menu", A = Ext
								.apply({
									pickerField : D,
									selModel : {
										mode : D.multiSelect ? "SIMPLE"
												: "SINGLE"
									},
									floating : true,
									hidden : true,
									ownerCt : D.ownerCt,
									cls : D.el.up("." + B) ? B : "",
									store : D.store,
									displayField : D.displayField,
									focusOnToFront : false,
									pageSize : D.pageSize,
									tpl : D.tpl
								}, D.listConfig, D.defaultListConfig);
						C = D.picker = Ext.create("Ext.view.BoundList", A);
						if (D.pageSize) {
							C.pagingToolbar.on("beforechange", D.onPageChange,
									D)
						}
						D.mon(C, {
							itemclick : D.onItemClick,
							refresh : D.onListRefresh,
							scope : D
						});
						D.mon(C.getSelectionModel(), {
							"beforeselect" : D.onBeforeSelect,
							"beforedeselect" : D.onBeforeDeselect,
							"selectionchange" : D.onListSelectionChange,
							scope : D
						});
						return C
					},
					alignPicker : function() {
						var E = this, D = E.picker, B = E.getPosition()[1]
								- Ext.getBody().getScroll().top, C = Ext.Element
								.getViewHeight()
								- B - E.getHeight(), A = Math.max(B, C);
						E.callParent();
						if (D.getHeight() > A) {
							D.setHeight(A - 5);
							E.doAlign()
						}
					},
					onListRefresh : function() {
						this.alignPicker();
						this.syncSelection()
					},
					onItemClick : function(E, C) {
						var F = this, D = F.lastSelection, A = F.valueField, B;
						if (!F.multiSelect && D) {
							B = D[0];
							if (B && (C.get(A) === B.get(A))) {
								F.displayTplData = [ C.data ];
								F.setRawValue(F.getDisplayValue());
								F.collapse()
							}
						}
					},
					onBeforeSelect : function(B, A) {
						return this.fireEvent("beforeselect", this, A, A.index)
					},
					onBeforeDeselect : function(B, A) {
						return this.fireEvent("beforedeselect", this, A,
								A.index)
					},
					onListSelectionChange : function(C, B) {
						var E = this, A = E.multiSelect, D = B.length > 0;
						if (!E.ignoreSelection && E.isExpanded) {
							if (!A) {
								Ext.defer(E.collapse, 1, E)
							}
							if (A || D) {
								E.setValue(B, false)
							}
							if (D) {
								E.fireEvent("select", E, B)
							}
							E.inputEl.focus()
						}
					},
					onExpand : function() {
						var D = this, B = D.listKeyNav, A = D.selectOnTab, C = D
								.getPicker();
						if (B) {
							B.enable()
						} else {
							B = D.listKeyNav = Ext.create(
									"Ext.view.BoundListKeyNav", this.inputEl, {
										boundList : C,
										forceKeyDown : true,
										tab : function(E) {
											if (A) {
												this.selectHighlighted(E);
												D.triggerBlur()
											}
											return true
										}
									})
						}
						if (A) {
							D.ignoreMonitorTab = true
						}
						Ext.defer(B.enable, 1, B);
						D.inputEl.focus()
					},
					onTrigger1Click : function() {
						var C = this;
						C.setValue();
						clearOrgHiddenValue();
						var A = C.cancel;
						C.setRawValue();
						if (A == "auto") {
							var B = C.triggerEl;
							B.elements[0].setDisplayed("none")
						}
					},
					onCollapse : function() {
						var D = this, C = D.listKeyNav;
						var A = D.cancel;
						if (C) {
							C.disable();
							D.ignoreMonitorTab = false
						}
						if (A == "auto") {
							var B = D.triggerEl;
							B.elements[0].setDisplayed("block")
						}
					},
					select : function(A) {
						this.setValue(A, true)
					},
					findRecord : function(C, A) {
						var D = this.store, B = D.findExact(C, A);
						return B !== -1 ? D.getAt(B) : false
					},
					findRecordByValue : function(A) {
						return this.findRecord(this.valueField, A)
					},
					findRecordByDisplay : function(A) {
						return this.findRecord(this.displayField, A)
					},
					setValue : function(D, H) {
						var G = this, I = G.valueNotFoundText, C = G.inputEl, A, J, K, F = [], B = [], E = [];
						if (G.store.loading) {
							G.value = D;
							G.setHiddenValue(G.value);
							return G
						}
						D = Ext.Array.from(D);
						for (A = 0, J = D.length; A < J; A++) {
							K = D[A];
							if (!K || !K.isModel) {
								K = G.findRecordByValue(K)
							}
							if (K) {
								F.push(K);
								B.push(K.data);
								E.push(K.get(G.valueField))
							} else {
								if (!G.forceSelection) {
									B.push(D[A]);
									E.push(D[A])
								} else {
									if (Ext.isDefined(I)) {
										B.push(I)
									}
								}
							}
						}
						G.setHiddenValue(E);
						G.value = G.multiSelect ? E : E[0];
						if (!Ext.isDefined(G.value)) {
							G.value = null
						}
						G.displayTplData = B;
						G.lastSelection = G.valueModels = F;
						if (C && G.emptyText && !Ext.isEmpty(D)) {
							C.removeCls(G.emptyCls)
						}
						G.setRawValue(G.getDisplayValue());
						G.checkChange();
						if (H !== false) {
							G.syncSelection()
						}
						G.applyEmptyText();
						return G
					},
					setHiddenValue : function(E) {
						var F = this, A;
						if (!F.hiddenDataEl) {
							return
						}
						E = Ext.Array.from(E);
						var H = F.hiddenDataEl.dom, G = H.childNodes, D = G[0], B = E.length, C = G.length;
						if (!D && B > 0) {
							F.hiddenDataEl.update(Ext.DomHelper.markup({
								tag : "input",
								type : "hidden",
								name : F.name
							}));
							C = 1;
							D = H.firstChild
						}
						while (C > B) {
							H.removeChild(G[0]);
							--C
						}
						while (C < B) {
							H.appendChild(D.cloneNode(true));
							++C
						}
						for (A = 0; A < B; A++) {
							G[A].value = E[A]
						}
					},
					getDisplayValue : function() {
						return this.displayTpl.apply(this.displayTplData)
					},
					getValue : function() {
						var D = this, C = D.picker, A = D.getRawValue(), B = D.value;
						if (D.getDisplayValue() !== A) {
							B = A;
							D.value = D.displayTplData = D.valueModels = null;
							if (C) {
								D.ignoreSelection++;
								C.getSelectionModel().deselectAll();
								D.ignoreSelection--
							}
						}
						return B
					},
					getSubmitValue : function() {
						return this.getValue()
					},
					isEqual : function(C, B) {
						var D = Ext.Array.from, E, A;
						C = D(C);
						B = D(B);
						A = C.length;
						if (A !== B.length) {
							return false
						}
						for (E = 0; E < A; E++) {
							if (B[E] !== C[E]) {
								return false
							}
						}
						return true
					},
					clearValue : function() {
						this.setValue([])
					},
					syncSelection : function() {
						var E = this, D = Ext.Array, A = E.picker, B, C;
						if (A) {
							B = [];
							D.forEach(E.valueModels || [], function(F) {
								if (F && F.isModel && E.store.indexOf(F) >= 0) {
									B.push(F)
								}
							});
							E.ignoreSelection++;
							C = A.getSelectionModel();
							C.deselectAll();
							if (B.length) {
								C.select(B)
							}
							E.ignoreSelection--
						}
					}
				});