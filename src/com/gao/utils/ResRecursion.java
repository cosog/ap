package com.gao.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.gao.model.Module;
import com.gao.model.Res;

/**<p>描述：递归处理工具类</p>
 * 
 * @author gao 2014-06-04
 * @version 1.0
 *
 */
@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
public class ResRecursion {
	private List nodeList = new ArrayList();
	StringBuffer returnStr = new StringBuffer();
	public ResRecursion() {// 构造方法里初始化模拟List

	}
	public String recursionResTreeFn(List list, Res node) {
		String data = "";
		if (hasChild(list, node)) {
			returnStr.append("{\"text\":\"" + node.getResName() + "\"");
			returnStr.append(",\"expanded\" : true");
			returnStr.append(",\"resId\":\"" + node.getResId() + "\"");
			returnStr.append(",\"resLevel\":\"" + node.getResLevel() + "\"");
			returnStr.append(",\"resParent\":\"" + node.getResParent() + "\"");
			returnStr.append(",\"resMemo\":\""
					+ (node.getResMemo() == null ? "无" : node.getResMemo())
					+ "\"");
			returnStr.append(",\"resSeq\":\""
					+ (node.getResSeq() == null ? "1" : node.getResSeq())
					+ "\"");
			returnStr.append(",\"resCode\":\"" + node.getResCode() + "\"");
			// returnStr.append(",\"iconCls\":\"Image\"");
			returnStr.append(",\"children\":[");
			List<?> childList = getChildList(list, node);
			Iterator<?> it = childList.iterator();
			while (it.hasNext()) {
				Res n = (Res) it.next();
				recursionResTreeFn(list, n);
			}
			returnStr.append("]},");
		} else {
			returnStr.append("{\"resId\":\"");
			returnStr.append(node.getResId());
			returnStr.append("\",\"resParent\":\"");
			returnStr.append(node.getResParent());
			returnStr.append("\",\"resLevel\":\"");
			returnStr.append(node.getResLevel());
			returnStr.append("\",\"resSeq\":\"");
			returnStr	.append((node.getResSeq() == null ? "1" : node.getResSeq()));
			returnStr.append("\",\"text\":\"");
			returnStr.append(node.getResName());
			returnStr.append("\",\"resCode\":\"");
			returnStr.append(node.getResCode());
			returnStr.append("\",\"resMemo\":\"");
			returnStr.append((node.getResMemo() == null ? "无" : node
					.getResMemo()));
			returnStr.append("\",\"leaf\":true },");
		}
		data = returnStr.toString();
		return data;
	}

	public boolean hasChild(List<?> list, Res node) { // 判断是否有子节点
		return getChildList(list, node).size() > 0 ? true : false;
	}

	public List getChildList(List list, Res node) { // 得到子节点列表
		List li = new ArrayList();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Res n = (Res) it.next();

			if (n.getResParent().equals(node.getResId())) {
				li.add(n);
			}
		}
		return li;
	}

	public boolean hasParent(List list, Res node) { // 判断是否有父节点
		return getParentList(list, node).size() > 0 ? true : false;
	}


	public List getParentList(List list, Res node) { // 得到子节点列表
		List li = new ArrayList();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Res n = (Res) it.next();
			if (n.getResId().equals(node.getResParent())) {
				li.add(n);
			}
		}
		return li;
	}
	public String modifyStr(String returnStr) {// 修饰一下才能满足Extjs的Json格式
		return ("[" + returnStr + "]").replaceAll(",]", "]");

	}
	public String modifyResStr(String returnStr) {// 修饰一下才能满足Extjs的Json格式
		return ("[" + returnStr + "]").replaceAll(",]", "]");

	}
	public static void main(String[] args) {
		ResRecursion r = new ResRecursion();
		// r.recursionFn(r.nodeList, new Node(1, 0));
		// System.out.println(r.modifyStr(r.returnStr.toString()));
	}
}