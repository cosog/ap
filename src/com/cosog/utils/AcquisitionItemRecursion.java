package com.cosog.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cosog.model.AcquisitionItem;

/**<p>描述：后太模块菜单递归工具类函数</p>
 * 
 * @author gao 2014-06-10
 *@version 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AcquisitionItemRecursion {
	StringBuffer returnStr = new StringBuffer();

	public AcquisitionItemRecursion() {// 构造方法里初始化模拟List
		
	}
	public String recursionAcquisitionItemTreeFn(List list, AcquisitionItem acquisitionItem) {

		String data = "";
		if (hasChild(list, acquisitionItem)) {
			returnStr.append("{\"text\":\"" + acquisitionItem.getItemName() + "\"");
			returnStr.append(",\"id\":\"" + acquisitionItem.getId() + "\"");
			returnStr.append(",\"parentid\":\"" + acquisitionItem.getParentid() + "\"");
			returnStr.append(",\"itemName\":\"" + acquisitionItem.getItemName() + "\"");
			returnStr.append(",\"itemCode\":\"" + acquisitionItem.getItemCode() + "\"");
			returnStr.append(",\"address\":\"" + acquisitionItem.getAddress() + "\"");
			returnStr.append(",\"length\":\"" + acquisitionItem.getLength() + "\"");
			returnStr.append(",\"dataType\":\"" + acquisitionItem.getDataType() + "\"");
			returnStr.append(",\"zoom\":\"" + acquisitionItem.getZoom() + "\"");
			returnStr.append(",\"checked\":false");
			returnStr.append(",\"expanded\":true");
			returnStr.append(",\"children\":[");
			List childList = getChildList(list, acquisitionItem);
			Iterator it = childList.iterator();
			while (it.hasNext()) {
				AcquisitionItem n = (AcquisitionItem) it.next();
				recursionAcquisitionItemTreeFn(list, n);
			}
			returnStr.append("]},");
		} else {
			returnStr.append("{\"text\":\"" + acquisitionItem.getItemName() + "\"");
			returnStr.append(",\"id\":\"" + acquisitionItem.getId() + "\"");
			returnStr.append(",\"parentid\":\"" + acquisitionItem.getParentid() + "\"");
			returnStr.append(",\"itemName\":\"" + acquisitionItem.getItemName() + "\"");
			returnStr.append(",\"itemCode\":\"" + acquisitionItem.getItemCode() + "\"");
			returnStr.append(",\"address\":\"" + acquisitionItem.getAddress() + "\"");
			returnStr.append(",\"length\":\"" + acquisitionItem.getLength() + "\"");
			returnStr.append(",\"dataType\":\"" + acquisitionItem.getDataType() + "\"");
			returnStr.append(",\"zoom\":\"" + acquisitionItem.getZoom() + "\"");
			returnStr.append(",\"checked\":false");
			returnStr.append(",\"leaf\":true},");
		}
		data = returnStr.toString();
		return data;
	}

	public boolean hasChild(List list, AcquisitionItem acquisitionItem) { // 判断是否有子节点
		return getChildList(list, acquisitionItem).size() > 0 ? true : false;
	}

	public List getChildList(List list, AcquisitionItem acquisitionItem) { // 得到子节点列表
		List li = new ArrayList();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			AcquisitionItem n = (AcquisitionItem) it.next();
			if (n.getParentid().equals(acquisitionItem.getId())) {
				li.add(n);
			}
		}
		return li;
	}

	public boolean hasParent(List list, AcquisitionItem node) { // 判断是否有父节点
		return getParentList(list, node).size() > 0 ? true : false;
	}

	public List getParentList(List list, AcquisitionItem node) { // 得到子节点列表
		List li = new ArrayList();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			AcquisitionItem n = (AcquisitionItem) it.next();
			if (n.getId().equals(node.getParentid())) {
				li.add(n);
			}
		}
		return li;
	}

	public String modifyStr(String returnStr) {// 修饰一下才能满足Extjs的Json格式
		return ("[" + returnStr + "]").replaceAll(",]", "]");

	}
}