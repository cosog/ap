package com.cosog.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**<p>描述：tab树形treePanel递归类</p>
 * 
 * @author zhao 2024-01-09
 *@version 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TabInfoRecursion {
	StringBuffer returnStr = new StringBuffer();

	public TabInfoRecursion() {// 构造方法里初始化模拟List

	}

	public String recursionTabFn(List list, Object[] node) {
		String data = "";
		if (hasChild(list, node)) {
			returnStr.append("{\"text\":\"" + node[2] + "\",");
			returnStr.append("\"expanded\" : true,");
			returnStr.append("\"tabId\":\"" +  node[0]+ "\",");
			returnStr.append("\"parentId\":\"" +  node[1]+ "\",");
			returnStr.append("\"sortNum\":\"" +  node[3]+ "\",");
			returnStr.append("\"children\":[");
			List childList = getChildList(list, node);
			Iterator it = childList.iterator();
			while (it.hasNext()) {
				Object[] n = (Object[]) it.next();
				recursionTabFn(list, n);
			}
			returnStr.append("]},");
		} else {
			returnStr.append("{\"text\":\"" + node[2] + "\",");
			returnStr.append("\"tabId\":\"" +  node[0]+ "\",");
			returnStr.append("\"parentId\":\"" +  node[1]+ "\",");
			returnStr.append("\"sortNum\":\"" +  node[3]+ "\",");
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

	public String modifyTabStr(String returnStr) {// 修饰一下才能满足Extjs的Json格式
		return ("[" + returnStr + "]").replaceAll(",]", "]");

	}
}