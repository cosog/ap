package com.cosog.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cosog.model.Module;

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
	public String modifyStr(String returnStr) {// 修饰一下才能满足Extjs的Json格式
		return ("[" + returnStr + "]").replaceAll(",]", "]");

	}
	public String modifyResStr(String returnStr) {// 修饰一下才能满足Extjs的Json格式
		return ("[" + returnStr + "]").replaceAll(",]", "]");

	}
	public static void main(String[] args) {
		ResRecursion r = new ResRecursion();
		// r.recursionFn(r.nodeList, new Node(1, 0));
		// StringManagerUtils.printLog(r.modifyStr(r.returnStr.toString()));
	}
}