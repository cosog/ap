package com.cosog.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cosog.model.Module;
import com.cosog.model.RoleModule;

/**<p>描述：后太模块菜单递归工具类函数</p>
 * 
 * @author gao 2014-06-10
 *@version 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BackModuleRecursion {
	StringBuffer returnStr = new StringBuffer();

	public BackModuleRecursion() {// 构造方法里初始化模拟List
	}

	public String recursionModuleFn(List list, Module module,String language) {
		String data = "";
		String mdName="";
		if("zh_CN".equalsIgnoreCase(language)){
			mdName=module.getMdName_zh_CN();
		}else if("en".equalsIgnoreCase(language)){
			mdName=module.getMdName_en();
		}else if("ru".equalsIgnoreCase(language)){
			mdName=module.getMdName_ru();
		}
		if (hasChild(list, module)) {
			returnStr.append("{\"text\":\"" + mdName + "\"");
			returnStr.append(",\"expanded\":true");
			returnStr.append(",\"iconCls\":\"" + module.getMdIcon() + "\"");
			returnStr.append(",\"mdId\":\"" + module.getMdId() + "\"");
			returnStr.append(",\"children\":[");
			List childList = getChildList(list, module);
			Iterator it = childList.iterator();
			while (it.hasNext()) {
				Module n = (Module) it.next();
				recursionModuleFn(list, n,language);
			}
			returnStr.append("]},");
		} else {
			returnStr.append("{\"id\":\"");
			returnStr.append(StringManagerUtils.replaceAll(module.getMdCode()));
			returnStr.append("\",\"text\":\"");
			returnStr.append(mdName);
			returnStr.append("\",\"mdId\":\"");
			returnStr.append(module.getMdId());
			returnStr.append("\",\"md_icon\":\"");
			returnStr.append(module.getMdIcon());
			returnStr.append("\",\"viewsrc\":\"");
			returnStr.append(StringManagerUtils.replaceAll(module.getMdUrl()));
			returnStr.append("\",\"controlsrc\":\"");
			returnStr.append(module.getMdControl());
			returnStr.append("\",\"closable\":true");
			returnStr.append(",\"iconCls\":\"" + module.getMdIcon());
			returnStr.append("\",\"leaf\":true},");
		}
		data = returnStr.toString();
		return data;
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

	public String recursionModuleTreeFn(List list, Module module,String language) {
		String data = "";
		String mdName="";
		String showName="";
		if("zh_CN".equalsIgnoreCase(language)){
			mdName=module.getMdName_zh_CN();
			showName=module.getMdShowname_zh_CN();
		}else if("en".equalsIgnoreCase(language)){
			mdName=module.getMdName_en();
			showName=module.getMdShowname_en();
		}else if("ru".equalsIgnoreCase(language)){
			mdName=module.getMdName_ru();
			showName=module.getMdShowname_ru();
		}
		if (hasChild(list, module)) {
			returnStr.append("{\"text\":\"" + mdName + "\"");
			returnStr.append(",\"mdShowname\":\"" + StringManagerUtils.filterNull(showName) + "\"");
			returnStr.append(",\"mdUrl\":\"" + module.getMdUrl() + "\"");
			returnStr.append(",\"mdParentid\":\"" + module.getMdParentid() + "\"");
			returnStr.append(",\"mdControl\":\"" + module.getMdControl() + "\"");
			returnStr.append(",\"mdIcon\":\"" + module.getMdIcon() + "\"");
			returnStr.append(",\"mdCode\":\"" + module.getMdCode() + "\"");
			returnStr.append(",\"mdType\":\"" + module.getMdType() + "\"");
			returnStr.append(",\"mdSeq\":\"" + module.getMdSeq() + "\"");
			returnStr.append(",\"mdId\":\"" + module.getMdId() + "\"");
			returnStr.append(",\"expanded\":false");
			returnStr.append(",\"children\":[");
			List childList = getChildList(list, module);
			Iterator it = childList.iterator();
			while (it.hasNext()) {
				Module n = (Module) it.next();
				recursionModuleTreeFn(list, n,language);
			}
			returnStr.append("]},");
		} else {
			returnStr.append("{\"mdId\":\"");
			returnStr.append(module.getMdId());
			returnStr.append("\",\"text\":\"");
			returnStr.append(mdName);
			returnStr.append("\",\"mdShowname\":\"");
			returnStr.append(StringManagerUtils.filterNull(showName));
			returnStr.append("\",\"mdParentid\":\"");
			returnStr.append(module.getMdParentid());
			returnStr.append("\",\"mdIcon\":\"");
			returnStr.append(module.getMdIcon());
			returnStr.append("\",\"mdUrl\":\"");
			returnStr.append(module.getMdUrl());
			returnStr.append("\",\"mdControl\":\"");
			returnStr.append(module.getMdControl());
			returnStr.append("\",\"mdCode\":\"");
			returnStr.append(module.getMdCode());
			returnStr.append("\",\"mdType\":\"");
			returnStr.append(module.getMdType());
			returnStr.append("\",\"mdSeq\":\"");
			returnStr.append(module.getMdSeq());
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

	public String recursionRightModuleTreeFn(List list, Module module,String language) {
		String data = "";
		String mdName="";
		String showName="";
		if("zh_CN".equalsIgnoreCase(language)){
			mdName=module.getMdName_zh_CN();
			showName=module.getMdShowname_zh_CN();
		}else if("en".equalsIgnoreCase(language)){
			mdName=module.getMdName_en();
			showName=module.getMdShowname_en();
		}else if("ru".equalsIgnoreCase(language)){
			mdName=module.getMdName_ru();
			showName=module.getMdShowname_ru();
		}
		if (hasChild(list, module)) {
			returnStr.append("{\"text\":\"" + mdName + "\",");
			returnStr.append("\"mdShowname\":\"" + showName + "\",");
			returnStr.append("\"mdUrl\":\"" + module.getMdUrl() + "\",");
			returnStr.append("\"mdParentid\":\"" + module.getMdParentid() + "\",");
			returnStr.append("\"mdControl\":\"" + module.getMdControl() + "\",");
			returnStr.append("\"mdIcon\":\"" + module.getMdIcon() + "\",");
			returnStr.append("\"iconCls\":\"" + module.getMdIcon() + "\",");
			returnStr.append("\"mdCode\":\"" + module.getMdCode() + "\",");
			returnStr.append("\"mdType\":\"" + module.getMdType() + "\",");
			returnStr.append("\"mdSeq\":\"" + module.getMdSeq() + "\",");
			returnStr.append("\"mdId\":\"" + module.getMdId() + "\",");
//			returnStr.append("\"checked\":false,");
			returnStr.append("\"viewFlagName\":false,");
			returnStr.append("\"editFlagName\":false,");
			returnStr.append("\"controlFlagName\":false,");
			returnStr.append("\"expanded\":true,");
			returnStr.append("\"children\":[");
			List childList = getChildList(list, module);
			Iterator it = childList.iterator();
			while (it.hasNext()) {
				Module n = (Module) it.next();
				recursionRightModuleTreeFn(list, n,language);
			}
			returnStr.append("]},");
		} else {
			returnStr.append("{\"mdId\":\""+module.getMdId()+"\",");
			returnStr.append("\"text\":\""+mdName+"\",");
			returnStr.append("\"mdShowname\":\""+showName+"\",");
			returnStr.append("\"mdParentid\":\""+module.getMdParentid()+"\",");
			returnStr.append("\"mdIcon\":\""+module.getMdIcon()+"\",");
			returnStr.append("\"iconCls\":\""+module.getMdIcon()+"\",");
			returnStr.append("\"mdUrl\":\""+module.getMdUrl()+"\",");
			returnStr.append("\"mdControl\":\""+module.getMdControl()+"\",");
			returnStr.append("\"mdCode\":\""+module.getMdCode()+"\",");
			returnStr.append("\"mdType\":\""+module.getMdType()+"\",");
			returnStr.append("\"mdSeq\":\""+module.getMdSeq()+"\",");
//			returnStr.append("\"checked\":false,");
			returnStr.append("\"viewFlagName\":false,");
			returnStr.append("\"editFlagName\":false,");
			returnStr.append("\"controlFlagName\":false,");
			returnStr.append("\"leaf\":true},");
		}
		data = returnStr.toString();
		return data;
	}

	public boolean hasChild(List list, Module module) { // 判断是否有子节点
		return getChildList(list, module).size() > 0 ? true : false;
	}

	public List getChildList(List list, Module module) { // 得到子节点列表
		List li = new ArrayList();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Module n = (Module) it.next();
			if (n.getMdParentid().equals(module.getMdId())) {
				li.add(n);
			}
		}
		return li;
	}

	public boolean hasParent(List list, Module node) { // 判断是否有父节点
		return getParentList(list, node).size() > 0 ? true : false;
	}

	public List getParentList(List list, Module node) { // 得到子节点列表
		List li = new ArrayList();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Module n = (Module) it.next();
			if (n.getMdId().equals(node.getMdParentid())) {
				li.add(n);
			}
		}
		return li;
	}

	public String modifyStr(String returnStr) {// 修饰一下才能满足Extjs的Json格式
		return ("[" + returnStr + "]").replaceAll(",]", "]");

	}

	public static void main(String[] args) {
	//	BackModuleRecursion r = new BackModuleRecursion();
		// r.recursionFn(r.moduleList, new module(1, 0));
	}
}