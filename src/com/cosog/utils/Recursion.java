package com.cosog.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cosog.model.Org;

/**<p>描述：组织树形菜单递归类</p>
 * 
 * @author gao 2014-06-10
 *@version 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class Recursion {
	StringBuffer returnStr = new StringBuffer();

	public Recursion() {// 构造方法里初始化模拟List

	}

	public String recursionFn(List list, Org node) {

		String data = "";
		if (hasChild(list, node)) {
			// returnStr.append("{ \"orgId:\"");
			// returnStr.append(node.getOrgId());
			returnStr.append("{\"text\":\"" + node.getOrgName() + "\"");
			returnStr.append(",\"expanded\" : true");
			returnStr.append(",\"orgId\":\"" + node.getOrgId() + "\"");
			returnStr.append(",\"orgParent\":\"" + node.getOrgParent() + "\"");
			returnStr.append(",\"orgCode\":\"");
			returnStr.append(node.getOrgCode());
//			if (node.getOrgParent() == 0) {
//				returnStr.append(",\"iconCls\":\"tree_root_icon\"");
//			} else {
//				returnStr.append(",\"iconCls\":\"tree_node_icon\"");
//			}
			returnStr.append("\",\"children\":[");
			List childList = getChildList(list, node);
			Iterator it = childList.iterator();
			while (it.hasNext()) {
				Org n = (Org) it.next();
				recursionFn(list, n);
			}
			returnStr.append("]},");
		} else {
			returnStr.append("{\"orgId\":\"");
			returnStr.append(node.getOrgId());
			returnStr.append("\",\"orgParent\":\"");
			returnStr.append(node.getOrgParent());
			returnStr.append("\",\"text\":\"");
			returnStr.append(node.getOrgName());
			returnStr.append("\",\"orgCode\":\"");
			returnStr.append(node.getOrgCode());
			//returnStr.append("\",\"iconCls\":\"tree_leaf_icon");
			returnStr.append("\",\"leaf\":true },");
		}
		data = returnStr.toString();
		return data;
	}
	
	public String recursionOrgCombTree(List list, Org node) {

		String data = "";
		if (hasChild(list, node)) {
			returnStr.append("{\"text\":\"" + node.getOrgName() + "\",");
			returnStr.append("\"expanded\":true,");
			returnStr.append("\"id\":" + node.getOrgId() + ",");
			returnStr.append("\"orgParent\":" + node.getOrgParent() + ",");
			returnStr.append("\"orgCode\":\""+node.getOrgCode()+"\",");
			returnStr.append("\"children\":[");
			List childList = getChildList(list, node);
			Iterator it = childList.iterator();
			while (it.hasNext()) {
				Org n = (Org) it.next();
				recursionOrgCombTree(list, n);
			}
			returnStr.append("]},");
		} else {
			returnStr.append("{\"id\":"+node.getOrgId()+",");
			returnStr.append("\"orgParent\":"+node.getOrgParent()+",");
			returnStr.append("\"text\":\""+node.getOrgName()+"\",");
			returnStr.append("\"orgCode\":\""+node.getOrgCode()+"\",");
			returnStr.append("\"leaf\":true },");
		}
		data = returnStr.toString();
		return data;
	}
	
	
	public String recursionMObileOrgTree(List list, Org node) {

		String data = "";
		if (hasChild(list, node)) {
			// returnStr.append("{ \"orgId:\"");
			// returnStr.append(node.getOrgId());
			returnStr.append("{\"text\":\"" + node.getOrgName() + "\",");
			returnStr.append("\"orgId\":\"" + node.getOrgId() + "\",");
			returnStr.append("\"children\":[");
			List childList = getChildList(list, node);
			Iterator it = childList.iterator();
			while (it.hasNext()) {
				Org n = (Org) it.next();
				recursionMObileOrgTree(list, n);
			}
			returnStr.append("]},");
		} else {
			returnStr.append("{\"orgId\":\""+node.getOrgId()+"\",");
			returnStr.append("\"text\":\""+node.getOrgName()+"\"},");
		}
		data = returnStr.toString();
		return data;
	}

	public static String judgeOrgType(String key) {
		int temp = StringManagerUtils.stringTransferInteger(key);
		String result = "局级";
		switch (temp) {
		case 1:
			result = "局级";
			break;
		case 2:
			result = "厂级";
			break;
		case 3:
			result = "矿级";
			break;
		case 4:
			result = "队级";
			break;
		case 5:
			result = "其他";
			break;
		default:
			result = "其他";
			break;
		}
		return result;

	}

	public String recursionOrgFn(List list, Org node) {
		String data = "";
		if (hasChild(list, node)) {
			returnStr.append("{\"text\":\"" + node.getOrgName() + "\"");
			returnStr.append(",\"expanded\" : true");
			returnStr.append(",\"orgId\":\"" + node.getOrgId() + "\"");
			returnStr.append(",\"orgParent\":\"" + node.getOrgParent() + "\"");
			returnStr.append(",\"orgMemo\":\""+ (node.getOrgMemo() == null ? "无" : node.getOrgMemo()) + "\"");
			returnStr.append(",\"orgCode\":\"" + node.getOrgCode() + "\"");
			returnStr.append(",\"children\":[");
			List childList = getChildList(list, node);
			Iterator it = childList.iterator();
			while (it.hasNext()) {
				Org n = (Org) it.next();
				recursionOrgFn(list, n);
			}
			returnStr.append("]},");
		} else {
			returnStr.append("{\"orgId\":\"");
			returnStr.append(node.getOrgId());
			returnStr.append("\",\"orgParent\":\"");
			returnStr.append(node.getOrgParent());
			returnStr.append("\",\"text\":\"");
			returnStr.append(node.getOrgName());
			returnStr.append("\",\"orgCode\":\"");
			returnStr.append(node.getOrgCode());
			returnStr.append("\",\"orgMemo\":\"");
			returnStr.append((node.getOrgMemo() == null ? "无" : node.getOrgMemo()));
			returnStr.append("\",\"leaf\":true },");
		}
		data = returnStr.toString();
		return data;
	}

	public boolean hasChild(List list, Org node) { // 判断是否有子节点
		return getChildList(list, node).size() > 0 ? true : false;
	}
	public boolean isParentNode(String[] strArr, int tid) { // 判断是否是当前用户的父节点
		boolean result=false;
		for(int i=0;i<strArr.length;i++){
			if(StringManagerUtils.isNotNull(strArr[i])&&tid==Integer.parseInt(strArr[i])){
				result=true;
				break;
			}
		}
		return result;
	}
	public boolean hasParent(List list, Org node) { // 判断是否有父节点
		return getParentList(list, node).size() > 0 ? true : false;
	}
	public List getChildList(List list, Org node) { // 得到子节点列表
		List li = new ArrayList();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Org n = (Org) it.next();

			if (n.getOrgParent().equals(node.getOrgId())) {
				li.add(n);
			}
		}
		return li;
	}

	public List getParentList(List list, Org node) { // 得到子节点列表
		List li = new ArrayList();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Org n = (Org) it.next();
			if (n.getOrgId().equals(node.getOrgParent())) {
				li.add(n);
			}
		}
		return li;
	}
	public String modifyStr(String returnStr) {// 修饰一下才能满足Extjs的Json格式
		return ("[" + returnStr + "]").replaceAll(",]", "]");

	}

	public String modifyOrgStr(String returnStr) {// 修饰一下才能满足Extjs的Json格式
		return ("[" + returnStr + "]").replaceAll(",]", "]");

	}

	public static void main(String[] args) {
	//	Recursion r = new Recursion();
		// r.recursionFn(r.nodeList, new Node(1, 0));
		// StringManagerUtils.printLog(r.modifyStr(r.returnStr.toString()));
	}
}