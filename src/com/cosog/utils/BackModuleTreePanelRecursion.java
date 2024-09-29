package com.cosog.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cosog.model.RoleModule;

/**
 * <p>
 * 描述：后太模块菜单递归工具类函数
 * </p>
 * 
 * @author gao 2014-06-10
 * @version 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BackModuleTreePanelRecursion {
	StringBuffer returnStr = new StringBuffer();

	public BackModuleTreePanelRecursion() {// 构造方法里初始化模拟List
	}

	public static String judgeModuleType(int key) {
		// int temp = StringManagerUtils.stringToInteger(key);
		String result = "前台模块";
		switch (key) {
		case 0:
			result = "前台模块";
			break;
		case 1:
			result = "后台模块";
			break;

		default:
			result = "后台模块";
			break;
		}
		return result;

	}

	public String recursionModuleTreeFn(List list, Object[] module) {
		String data = "";
		if (hasChild(list, module)) {
			returnStr.append("{\"text\":\"" + module[1] + "\"");
			returnStr.append(",\"mdShowname\":\"" + StringManagerUtils.filterNull(module[3] + "") + "\"");
			returnStr.append(",\"mdUrl\":\"" + module[4] + "\"");
			returnStr.append(",\"mdParentid\":\"" + module[2] + "\"");
			returnStr.append(",\"mdControl\":\"" + module[9] + "\"");
			returnStr.append(",\"mdIcon\":\"" + module[7] + "\"");
			returnStr.append(",\"iconCls\":\"" + module[7] + "\"");
			returnStr.append(",\"mdCode\":\"" + module[5] + "\"");
			returnStr.append(",\"mdType\":\"" + module[8] + "\"");
			returnStr.append(",\"mdTypeName\":\"" + module[10] + "\"");
			returnStr.append(",\"mdSeq\":\"" + module[6] + "\"");
			returnStr.append(",\"mdId\":\"" + module[0] + "\"");
			returnStr.append(",\"expanded\":true");
			returnStr.append(",\"children\":[");
			List childList = getChildList(list, module);
			Iterator it = childList.iterator();
			while (it.hasNext()) {
				Object[] n = (Object[]) it.next();
				recursionModuleTreeFn(list, n);
			}
			returnStr.append("]},");
		} else {
			returnStr.append("{\"mdId\":\"");
			returnStr.append(module[0]);
			returnStr.append("\",\"text\":\"");
			returnStr.append(module[1]);
			returnStr.append("\",\"mdShowname\":\"");
			returnStr.append(StringManagerUtils.filterNull(module[3] + ""));
			returnStr.append("\",\"mdParentid\":\"");
			returnStr.append(module[2]);
			returnStr.append("\",\"mdIcon\":\"");
			returnStr.append(module[7]);
			returnStr.append("\",\"iconCls\":\"");
			returnStr.append(module[7]);
			returnStr.append("\",\"mdUrl\":\"");
			returnStr.append(module[4]);
			returnStr.append("\",\"mdControl\":\"");
			returnStr.append(module[9]);
			returnStr.append("\",\"mdCode\":\"");
			returnStr.append(module[5]);
			returnStr.append("\",\"mdType\":\"");
			returnStr.append(module[8]);
			returnStr.append("\",\"mdTypeName\":\"");
			returnStr.append(module[10]);
			returnStr.append("\",\"mdSeq\":\"");
			returnStr.append(module[6]);
			returnStr.append("\",\"leaf\":true},");
		}
		data = returnStr.toString();
		return data;
	}

	public boolean roleOwnModules(int curMdId, List<RoleModule> ownModules) {
		boolean flag = false;
		for (RoleModule m : ownModules) {
			if (m.getRmModuleid() == curMdId) {
				flag = true;
			}
		}
		return flag;
	}

	public boolean hasChild(List list, Object[] module) { // 判断是否有子节点
		return getChildList(list, module).size() > 0 ? true : false;
	}

	public List getChildList(List list, Object[] module) { // 得到子节点列表
		List li = new ArrayList();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Object[] n = (Object[]) it.next();
			if (n[2].equals(module[0])) {
				li.add(n);
			}
		}
		return li;
	}

	public boolean hasParent(List list, Object[] node) { // 判断是否有父节点
		return getParentList(list, node).size() > 0 ? true : false;
	}

	public List getParentList(List list,  Object[]  node) { // 得到子节点列表
		List li = new ArrayList();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Object[]   n = (Object[]  ) it.next();
			if ( n[0] .equals(node[2])) {
				li.add(n);
			}
		}
		return li;
	}

	public String modifyStr(String returnStr) {// 修饰一下才能满足Extjs的Json格式
		return ("[" + returnStr + "]").replaceAll(",]", "]");

	}

	public static void main(String[] args) {
		// BackModuleRecursion r = new BackModuleRecursion();
		// r.recursionFn(r.moduleList, new module(1, 0));
	}
}