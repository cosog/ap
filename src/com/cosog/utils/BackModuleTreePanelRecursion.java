package com.cosog.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cosog.model.ExportModuleData;
import com.cosog.model.RoleModule;
import com.cosog.task.MemoryDataManagerTask;

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

	public String recursionModuleTreeFn(List list, Object[] module,String language) {
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
			returnStr.append(",\"mdTypeName\":\"" + MemoryDataManagerTask.getCodeName("MD_TYPE",module[8]+"", language) + "\"");
			returnStr.append(",\"mdSeq\":\"" + module[6] + "\"");
			returnStr.append(",\"mdId\":\"" + module[0] + "\"");
			
			returnStr.append(",\"mdName_zh_CN\":\"" + module[10] + "\"");
			returnStr.append(",\"mdName_en\":\"" + module[11] + "\"");
			returnStr.append(",\"mdName_ru\":\"" + module[12] + "\"");
			
			returnStr.append(",\"mdShowname_zh_CN\":\"" + module[13] + "\"");
			returnStr.append(",\"mdShowname_en\":\"" + module[14] + "\"");
			returnStr.append(",\"mdShowname_ru\":\"" + module[15] + "\"");
			
			returnStr.append(",\"expanded\":true");
			returnStr.append(",\"children\":[");
			List childList = getChildList(list, module);
			Iterator it = childList.iterator();
			while (it.hasNext()) {
				Object[] n = (Object[]) it.next();
				recursionModuleTreeFn(list, n,language);
			}
			returnStr.append("]},");
		} else {
			returnStr.append("{\"mdId\":\""+module[0]+"\",");
			returnStr.append("\"text\":\""+module[1]+"\",");
			returnStr.append("\"mdShowname\":\""+StringManagerUtils.filterNull(module[3] + "")+"\",");
			returnStr.append("\"mdParentid\":\""+module[2]+"\",");
			returnStr.append("\"mdIcon\":\""+module[7]+"\",");
			returnStr.append("\"iconCls\":\""+module[7]+"\",");
			returnStr.append("\"mdUrl\":\""+module[4]+"\",");
			returnStr.append("\"mdControl\":\""+module[9]+"\",");
			returnStr.append("\"mdCode\":\""+module[5]+"\",");
			returnStr.append("\"mdType\":\""+module[8]+"\",");
			returnStr.append("\"mdTypeName\":\""+MemoryDataManagerTask.getCodeName("MD_TYPE",module[8]+"", language)+"\",");
			returnStr.append("\"mdSeq\":\""+module[6]+"\",");
			returnStr.append("\"mdName_zh_CN\":\"" + module[10] + "\",");
			returnStr.append("\"mdName_en\":\"" + module[11] + "\",");
			returnStr.append("\"mdName_ru\":\"" + module[12] + "\",");
			returnStr.append("\"mdShowname_zh_CN\":\"" + module[13] + "\",");
			returnStr.append("\"mdShowname_en\":\"" + module[14] + "\",");
			returnStr.append("\"mdShowname_ru\":\"" + module[15] + "\",");
			returnStr.append("\"leaf\":true},");
		}
		data = returnStr.toString();
		return data;
	}
	
	public String recursionModuleTreeFn(List<ExportModuleData> uploadModuleList, ExportModuleData exportModuleData,String language) {
		String data = "";
		String name="";
		String showName="";
		if("zh_CN".equalsIgnoreCase(language)){
			name=exportModuleData.getModuleName_zh_CN();
			showName=exportModuleData.getModuleShowName_zh_CN();
		}else if("en".equalsIgnoreCase(language)){
			name=exportModuleData.getModuleName_en();
			showName=exportModuleData.getModuleShowName_en();
		}else if("ru".equalsIgnoreCase(language)){
			name=exportModuleData.getModuleName_ru();
			showName=exportModuleData.getModuleShowName_ru();
		}
		
		if (hasChild(uploadModuleList, exportModuleData)) {
			returnStr.append("{\"text\":\"" + name + "\"");
			returnStr.append(",\"mdShowname\":\"" + showName + "\"");
			returnStr.append(",\"mdUrl\":\"" + exportModuleData.getModuleUrl() + "\"");
			returnStr.append(",\"mdParentid\":\"" + exportModuleData.getModuleParentId() + "\"");
			returnStr.append(",\"mdControl\":\"" + exportModuleData.getModuleControl() + "\"");
			returnStr.append(",\"mdIcon\":\"" + exportModuleData.getModuleIcon() + "\"");
			returnStr.append(",\"iconCls\":\"" + exportModuleData.getModuleIcon() + "\"");
			returnStr.append(",\"mdCode\":\"" + exportModuleData.getModuleCode() + "\"");
			returnStr.append(",\"mdType\":\"" + exportModuleData.getModuleType() + "\"");
			returnStr.append(",\"mdTypeName\":\"" + MemoryDataManagerTask.getCodeName("MD_TYPE",exportModuleData.getModuleType()+"", language) + "\"");
			returnStr.append(",\"mdSeq\":\"" + exportModuleData.getModuleSeq() + "\"");
			returnStr.append(",\"mdId\":\"" + exportModuleData.getModuleId() + "\"");
			
			returnStr.append(",\"mdName_zh_CN\":\"" + exportModuleData.getModuleName_zh_CN() + "\"");
			returnStr.append(",\"mdName_en\":\"" + exportModuleData.getModuleName_en() + "\"");
			returnStr.append(",\"mdName_ru\":\"" + exportModuleData.getModuleName_ru() + "\"");
			
			returnStr.append(",\"mdShowname_zh_CN\":\"" + exportModuleData.getModuleShowName_zh_CN() + "\"");
			returnStr.append(",\"mdShowname_en\":\"" + exportModuleData.getModuleShowName_en() + "\"");
			returnStr.append(",\"mdShowname_ru\":\"" + exportModuleData.getModuleShowName_ru() + "\"");
			
			returnStr.append(",\"msg\":\"" + exportModuleData.getMsg() + "\"");
			returnStr.append(",\"saveSign\":" + exportModuleData.getSaveSign() + "");
			
			returnStr.append(",\"expanded\":true");
			returnStr.append(",\"children\":[");
			List<ExportModuleData> childList = getChildList(uploadModuleList, exportModuleData);
			for (ExportModuleData n:childList) {
				recursionModuleTreeFn(uploadModuleList, n,language);
			}
			returnStr.append("]},");
		} else {
			returnStr.append("{\"mdId\":\""+exportModuleData.getModuleId()+"\",");
			returnStr.append("\"text\":\""+name+"\",");
			returnStr.append("\"mdShowname\":\""+showName+"\",");
			returnStr.append("\"mdParentid\":\""+exportModuleData.getModuleParentId()+"\",");
			returnStr.append("\"mdIcon\":\""+exportModuleData.getModuleIcon()+"\",");
			returnStr.append("\"iconCls\":\""+exportModuleData.getModuleIcon()+"\",");
			returnStr.append("\"mdUrl\":\""+exportModuleData.getModuleUrl()+"\",");
			returnStr.append("\"mdControl\":\""+exportModuleData.getModuleControl()+"\",");
			returnStr.append("\"mdCode\":\""+exportModuleData.getModuleCode()+"\",");
			returnStr.append("\"mdType\":\""+exportModuleData.getModuleType()+"\",");
			returnStr.append("\"mdTypeName\":\""+MemoryDataManagerTask.getCodeName("MD_TYPE",exportModuleData.getModuleType()+"", language)+"\",");
			returnStr.append("\"mdSeq\":\""+exportModuleData.getModuleSeq()+"\",");
			returnStr.append("\"mdName_zh_CN\":\"" + exportModuleData.getModuleName_zh_CN() + "\",");
			returnStr.append("\"mdName_en\":\"" + exportModuleData.getModuleName_en() + "\",");
			returnStr.append("\"mdName_ru\":\"" + exportModuleData.getModuleName_ru() + "\",");
			returnStr.append("\"mdShowname_zh_CN\":\"" + exportModuleData.getModuleShowName_zh_CN() + "\",");
			returnStr.append("\"mdShowname_en\":\"" + exportModuleData.getModuleShowName_en() + "\",");
			returnStr.append("\"mdShowname_ru\":\"" + exportModuleData.getModuleShowName_ru() + "\",");
			returnStr.append("\"msg\":\"" + exportModuleData.getMsg() + "\",");
			returnStr.append("\"saveSign\":" + exportModuleData.getSaveSign() + ",");
			returnStr.append("\"leaf\":true},");
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
	
	public boolean hasChild(List<ExportModuleData> uploadModuleList, ExportModuleData exportModuleData) { // 判断是否有子节点
		return getChildList(uploadModuleList, exportModuleData).size() > 0 ? true : false;
	}

	public List<ExportModuleData> getChildList(List<ExportModuleData> uploadModuleList, ExportModuleData exportModuleData) { // 得到子节点列表
		List<ExportModuleData> li = new ArrayList();
		Iterator it = uploadModuleList.iterator();
		for(ExportModuleData e:uploadModuleList){
			if(e.getModuleParentId()==exportModuleData.getModuleId()){
				li.add(e);
			}
		}
		return li;
	}

	public boolean hasParent(List<ExportModuleData> uploadModuleList, ExportModuleData exportModuleData) { // 判断是否有父节点
		return getParentList(uploadModuleList, exportModuleData).size() > 0 ? true : false;
	}

	public List<ExportModuleData> getParentList(List<ExportModuleData> uploadModuleList, ExportModuleData exportModuleData) { // 得到子节点列表
		List<ExportModuleData> li = new ArrayList();
		for(ExportModuleData e:uploadModuleList){
			if(e.getModuleId()==exportModuleData.getModuleParentId()){
				li.add(e);
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