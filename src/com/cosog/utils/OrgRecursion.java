package com.cosog.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cosog.model.ExportOrganizationData;


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
		int temp = StringManagerUtils.stringToInteger(key);
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
			returnStr.append("\"orgId\":\"" +  node[0]+ "\",");
			returnStr.append("\"orgParent\":\"" +  node[1]+ "\",");
			returnStr.append("\"orgMemo\":\"" +  node[3]+ "\",");
			returnStr.append("\"orgSeq\":\"" +  node[4]+ "\",");
			returnStr.append("\"orgName_zh_CN\":\"" +  node[5]+ "\",");
			returnStr.append("\"orgName_en\":\"" +  node[6]+ "\",");
			returnStr.append("\"orgName_ru\":\"" +  node[7]+ "\",");
			returnStr.append("\"expanded\" : true,");
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
			returnStr.append("\"orgName_zh_CN\":\"" +  node[5]+ "\",");
			returnStr.append("\"orgName_en\":\"" +  node[6]+ "\",");
			returnStr.append("\"orgName_ru\":\"" +  node[7]+ "\",");
			returnStr.append("\"leaf\":true },");
		}
		data = returnStr.toString().replaceAll("null", "");
		return data;
	}
	
	public String recursionOrgFn(List<ExportOrganizationData> list, ExportOrganizationData exportOrganizationData,String language) {
		String data = "";
		String text="";
		if("zh_CN".equalsIgnoreCase(language)){
			text=exportOrganizationData.getOrgName_zh_CN();
		}else if("en".equalsIgnoreCase(language)){
			text=exportOrganizationData.getOrgName_en();
		}else if("ru".equalsIgnoreCase(language)){
			text=exportOrganizationData.getOrgName_ru();
		}
		if (hasChild(list, exportOrganizationData)) {
			returnStr.append("{\"text\":\"" + text + "\",");
			returnStr.append("\"orgId\":\"" +  exportOrganizationData.getOrgId()+ "\",");
			returnStr.append("\"orgParent\":\"" +  exportOrganizationData.getOrgParentId()+ "\",");
			returnStr.append("\"orgMemo\":\"" +  exportOrganizationData.getOrgMemo()+ "\",");
			returnStr.append("\"orgSeq\":\"" +  exportOrganizationData.getOrgSeq()+ "\",");
			returnStr.append("\"orgName_zh_CN\":\"" +  exportOrganizationData.getOrgName_zh_CN()+ "\",");
			returnStr.append("\"orgName_en\":\"" +  exportOrganizationData.getOrgName_en()+ "\",");
			returnStr.append("\"orgName_ru\":\"" +  exportOrganizationData.getOrgName_ru()+ "\",");
			returnStr.append("\"saveSign\":" +  exportOrganizationData.getSaveSign()+ ",");
			returnStr.append("\"msg\":\"" +  exportOrganizationData.getMsg()+ "\",");
			returnStr.append("\"expanded\" : true,");
			returnStr.append("\"children\":[");
			List childList = getChildList(list, exportOrganizationData);
			Iterator it = childList.iterator();
			while (it.hasNext()) {
				ExportOrganizationData n = (ExportOrganizationData) it.next();
				recursionOrgFn(list, n,language);
			}
			returnStr.append("]},");
		} else {
			returnStr.append("{\"text\":\"" + text + "\",");
			returnStr.append("\"orgId\":\"" +  exportOrganizationData.getOrgId()+ "\",");
			returnStr.append("\"orgParent\":\"" +  exportOrganizationData.getOrgParentId()+ "\",");
			returnStr.append("\"orgMemo\":\"" +  exportOrganizationData.getOrgMemo()+ "\",");
			returnStr.append("\"orgSeq\":\"" +  exportOrganizationData.getOrgSeq()+ "\",");
			returnStr.append("\"orgName_zh_CN\":\"" +  exportOrganizationData.getOrgName_zh_CN()+ "\",");
			returnStr.append("\"orgName_en\":\"" +  exportOrganizationData.getOrgName_en()+ "\",");
			returnStr.append("\"orgName_ru\":\"" +  exportOrganizationData.getOrgName_ru()+ "\",");
			returnStr.append("\"saveSign\":" +  exportOrganizationData.getSaveSign()+ ",");
			returnStr.append("\"msg\":\"" +  exportOrganizationData.getMsg()+ "\",");
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
	
	public boolean hasChild(List<ExportOrganizationData> list, ExportOrganizationData exportOrganizationData) { // 判断是否有子节点
		return getChildList(list, exportOrganizationData).size() > 0 ? true : false;
	}
	
	public boolean hasParent(List<ExportOrganizationData> list, ExportOrganizationData exportOrganizationData) { // 判断是否有父节点
		return getParentList(list, exportOrganizationData).size() > 0 ? true : false;
	}
	
	public List getParentList(List<ExportOrganizationData> list, ExportOrganizationData exportOrganizationData) { // 得到父节点列表
		List<ExportOrganizationData> li = new ArrayList();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			ExportOrganizationData e = (ExportOrganizationData) it.next();
			if (e.getOrgId()==exportOrganizationData.getOrgParentId()) {
				li.add(e);
			}
		}
		return li;
	}
	
	public List getChildList(List<ExportOrganizationData> list, ExportOrganizationData exportOrganizationData) { // 得到子节点列表
		List<ExportOrganizationData> li = new ArrayList();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			ExportOrganizationData e = (ExportOrganizationData) it.next();
			if (e.getOrgParentId()==exportOrganizationData.getOrgId()) {
				li.add(e);
			}
		}
		return li;
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

	public List getParentList(List list, Object[] node) { // 得到父节点列表
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
}