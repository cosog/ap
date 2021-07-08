package com.cosog.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cosog.model.Params;

public class TestArray {
	public static void main(String[] args) {
//		String inputstr = "abbcddeeefffff";// 字符串
//		char[] array_input = inputstr.toCharArray();// 将字符串转化为字符数组
//		HashMap<Character, Integer> map = new HashMap<Character, Integer>();// 创建一个map<字符，个数>
//		for (int i = 0; i < array_input.length; i++) {
//			Character row = array_input[i];
//			if (map.containsKey(row)) {// 如果包含该字符
//				Integer count = map.get(array_input[i]) + 1;// 个数等于原有个数加一
//				map.remove(row);// 移除这个字符
//				map.put(row, count);// 加入字符
//			} else {// 不包含该字符
//				map.put(row, 1);// 加入该字符，并将个数赋为一
//			}
//		}
		String a="2";
		//String b="1";
		int  cljg=3;
		boolean  flag= "1".equals(a)&&cljg==1||cljg==2;
		boolean  flag1= "1".equals(a)&&(cljg==1||cljg==2);
		boolean  flag2= "1".equals(a)&&(cljg==2||cljg==1);
		boolean  flag3= "1".equals(a)&&cljg==1|| "1".equals(a)&&cljg==2;
		System.out.println("flag===="+flag+"         flag1 == "+flag1+" flag2 === "+flag2+" flag3=="+flag3);
//		Map<String, List<Params>> map	=getMapData();
//	Set<String> set=	map.keySet();
//	for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
//		String string =iterator.next();
//		List<Params> resusltList=map.get(string);
//		System.out.println("字符："+string+"对应的有以下"+resusltList.size()+"个\n【");
//		for (Params params : resusltList) {
//			System.out.println("当前字符："+params.getFields()+"");
//		}
//		System.out.println("】\n");
//	}
		//System.out.println(map);// 打印结果
	}

	public static Map<String, List<Params>> getMapData() {
		List<String> dataList = new ArrayList<String>();
		dataList.add("a");
		dataList.add("b");
		dataList.add("a");
		dataList.add("c");
		dataList.add("c");
		dataList.add("a");
		dataList.add("b");
		dataList.add("b");
		dataList.add("b");
		Params params = null;
		Map<String, List<Params>> map = new HashMap<String, List<Params>>();
		List<Params> resutlList = null;
		for (String s : dataList) {
			if (map.containsKey(s)) {
				params = new Params();
				params.setFields(s);
				resutlList = map.get(s);
				resutlList.add(params);
				map.remove(s);
				map.put(s, resutlList);
			} else {
				resutlList = new ArrayList<Params>();
				params = new Params();
				params.setFields(s);
				resutlList.add(params);
				map.put(s, resutlList);
			}
		}

		return map;

	}
}
