package com.gao.model;

public class OrgGridPanelData {
	private String text;
	private boolean leaf;
	private Integer orgId;
	private String orgCode;
	private String orgMemo;
	private String orgParent;
	private String orgLevel;
	private String orgType;
	private String orgTypeName;
	private String orgCoordX;
	private String orgCoordY;
	private String showLevel;
	private String threadid;
	private String parentId;
	private Integer index;
	private String depth;
	private boolean expanded;
	private boolean expandable;
	private String checked;
	private String cls;
	private String iconCls;
	private String icon;
	private boolean root;
	private boolean isLast;
	private boolean isFirst;
	private boolean allowDrop;
	private boolean allowDrag;
	private boolean loaded;
	private boolean loading;
    private String href;
    private String hrefTarget;
    private String qtip;
    private String qtitle;
    private Integer qshowDelay;
    private String children;
    private boolean visible;
	public OrgGridPanelData() {
		super();
	}
	public OrgGridPanelData(String text, boolean leaf, Integer orgId, String orgCode, String orgMemo, String orgParent,
			String orgLevel, String orgType, String orgTypeName, String orgCoordX, String orgCoordY, String showLevel,
			String threadid, String parentId, Integer index, String depth, boolean expanded, boolean expandable,
			String checked, String cls, String iconCls, String icon, boolean root, boolean isLast, boolean isFirst,
			boolean allowDrop, boolean allowDrag, boolean loaded, boolean loading, String href, String hrefTarget,
			String qtip, String qtitle, Integer qshowDelay, String children, boolean visible) {
		super();
		this.text = text;
		this.leaf = leaf;
		this.orgId = orgId;
		this.orgCode = orgCode;
		this.orgMemo = orgMemo;
		this.orgParent = orgParent;
		this.orgLevel = orgLevel;
		this.orgType = orgType;
		this.orgTypeName = orgTypeName;
		this.orgCoordX = orgCoordX;
		this.orgCoordY = orgCoordY;
		this.showLevel = showLevel;
		this.threadid = threadid;
		this.parentId = parentId;
		this.index = index;
		this.depth = depth;
		this.expanded = expanded;
		this.expandable = expandable;
		this.checked = checked;
		this.cls = cls;
		this.iconCls = iconCls;
		this.icon = icon;
		this.root = root;
		this.isLast = isLast;
		this.isFirst = isFirst;
		this.allowDrop = allowDrop;
		this.allowDrag = allowDrag;
		this.loaded = loaded;
		this.loading = loading;
		this.href = href;
		this.hrefTarget = hrefTarget;
		this.qtip = qtip;
		this.qtitle = qtitle;
		this.qshowDelay = qshowDelay;
		this.children = children;
		this.visible = visible;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public Integer getOrgId() {
		return orgId;
	}
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgMemo() {
		return orgMemo;
	}
	public void setOrgMemo(String orgMemo) {
		this.orgMemo = orgMemo;
	}
	public String getOrgParent() {
		return orgParent;
	}
	public void setOrgParent(String orgParent) {
		this.orgParent = orgParent;
	}
	public String getOrgLevel() {
		return orgLevel;
	}
	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public String getOrgTypeName() {
		return orgTypeName;
	}
	public void setOrgTypeName(String orgTypeName) {
		this.orgTypeName = orgTypeName;
	}
	public String getOrgCoordX() {
		return orgCoordX;
	}
	public void setOrgCoordX(String orgCoordX) {
		this.orgCoordX = orgCoordX;
	}
	public String getOrgCoordY() {
		return orgCoordY;
	}
	public void setOrgCoordY(String orgCoordY) {
		this.orgCoordY = orgCoordY;
	}
	public String getShowLevel() {
		return showLevel;
	}
	public void setShowLevel(String showLevel) {
		this.showLevel = showLevel;
	}
	public String getThreadid() {
		return threadid;
	}
	public void setThreadid(String threadid) {
		this.threadid = threadid;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public String getDepth() {
		return depth;
	}
	public void setDepth(String depth) {
		this.depth = depth;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public boolean isExpandable() {
		return expandable;
	}
	public void setExpandable(boolean expandable) {
		this.expandable = expandable;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getCls() {
		return cls;
	}
	public void setCls(String cls) {
		this.cls = cls;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public boolean isRoot() {
		return root;
	}
	public void setRoot(boolean root) {
		this.root = root;
	}
	public boolean isLast() {
		return isLast;
	}
	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}
	public boolean isFirst() {
		return isFirst;
	}
	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}
	public boolean isAllowDrop() {
		return allowDrop;
	}
	public void setAllowDrop(boolean allowDrop) {
		this.allowDrop = allowDrop;
	}
	public boolean isAllowDrag() {
		return allowDrag;
	}
	public void setAllowDrag(boolean allowDrag) {
		this.allowDrag = allowDrag;
	}
	public boolean isLoaded() {
		return loaded;
	}
	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}
	public boolean isLoading() {
		return loading;
	}
	public void setLoading(boolean loading) {
		this.loading = loading;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getHrefTarget() {
		return hrefTarget;
	}
	public void setHrefTarget(String hrefTarget) {
		this.hrefTarget = hrefTarget;
	}
	public String getQtip() {
		return qtip;
	}
	public void setQtip(String qtip) {
		this.qtip = qtip;
	}
	public String getQtitle() {
		return qtitle;
	}
	public void setQtitle(String qtitle) {
		this.qtitle = qtitle;
	}
	public Integer getQshowDelay() {
		return qshowDelay;
	}
	public void setQshowDelay(Integer qshowDelay) {
		this.qshowDelay = qshowDelay;
	}
	public String getChildren() {
		return children;
	}
	public void setChildren(String children) {
		this.children = children;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

    
}
