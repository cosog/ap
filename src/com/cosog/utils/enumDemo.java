package com.cosog.utils;

import java.util.Scanner;

public class enumDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * outter: for (int i = 0; i < 3; i++) { StringManagerUtils.printLog("iiii===>" +
		 * i); inner: for (int j = 0; j < 5; j++) { if (j == 1) continue inner;
		 * StringManagerUtils.printLog("j===>" + j); } }
		 */
		Scanner s = new Scanner(System.in);
		StringManagerUtils.printLog("请输入当前的星期 英文?\r \n",0);
		String day = s.next();// 输入的字符串
		Day index=Day.toDay(day.toUpperCase());
		switch (index) {
		case SUNDAY:
			StringManagerUtils.printLog("星期天",0);
			break;
		case MONDAY:
			StringManagerUtils.printLog("星期一",0);
			break;
		case TUESDAY:
			StringManagerUtils.printLog("星期二",0);
			break;

		}

	}

	public enum Day
	{
		SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, NOVALUE;
		public static Day toDay(String str)
		{
			try {
				return valueOf(str);
			} catch (Exception ex) {
				return NOVALUE;
			}
		}
	}
}
