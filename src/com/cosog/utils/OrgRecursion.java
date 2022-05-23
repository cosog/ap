package com.cosog.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**<p>描述：组织树形treePanel递归类</p>
 * 
 * @author gao 2014-06-10
 *@version 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class OrgRecursion {
	StringBuffer returnStr = new StringBuffer();

	public OrgRecursion() {// 构造方法里初始化模拟List

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

	public String recursionOrgFn(List list, Object[] node) {
		String data = "";
		if (hasChild(list, node)) {
			returnStr.append("{\"text\":\"" + node[2] + "\",");
			returnStr.append("\"expanded\" : true,");
			returnStr.append("\"orgId\":\"" +  node[0]+ "\",");
			returnStr.append("\"orgParent\":\"" +  node[1]+ "\",");
			returnStr.append("\"orgMemo\":\"" +  node[3]+ "\",");
			returnStr.append("\"orgSeq\":\"" +  node[4]+ "\",");
			returnStr.append("\"children\":[");
			List childList = getChildList(list, node);
			Iterator it = childList.iterator();
			while (it.hasNext()) {
				Object[] n = (Object[]) it.next();
				recursionOrgFn(list, n);
			}
			returnStr.append("]},");
		} else {
			returnStr.append("{\"text\":\"" + node[2] + "\",");
			returnStr.append("\"orgId\":\"" +  node[0]+ "\",");
			returnStr.append("\"orgParent\":\"" +  node[1]+ "\",");
			returnStr.append("\"orgMemo\":\"" +  node[3]+ "\",");
			returnStr.append("\"orgSeq\":\"" +  node[4]+ "\",");
			returnStr.append("\"leaf\":true },");
		}
		data = returnStr.toString().replaceAll("null", "");
		return data;
	}

	public boolean hasChild(List list, Object[] node) { // 判断是否有子节点
		return getChildList(list, node).size() > 0 ? true : false;
	}
	public boolean hasParent(List list, Object[] node) { // 判断是否有父节点
		return getParentList(list, node).size() > 0 ? true : false;
	}
	public List getChildList(List list, Object[] node) { // 得到子节点列表
		List li = new ArrayList();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Object[] n = (Object[] ) it.next();
			if (n[1].equals(node[0])) {
				li.add(n);
			}
		}
		return li;
	}

	public List getParentList(List list, Object[] node) { // 得到子节点列表
		List li = new ArrayList();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Object[] n = (Object[]) it.next();
			if (n[0].equals(node[1])) {
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