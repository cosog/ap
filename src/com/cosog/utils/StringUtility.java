package com.cosog.utils;

/**
 * <p>Title: Science Management Project</p>
 * <p>Description: Science Management Project</p>
 * <p>Created on 2008-8-26</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: JamieSoft </p>
 * @author Jamie Xu
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;
import java.sql.*;
import java.io.*;
import org.apache.log4j.*;

public class StringUtility {

	protected final static Logger logger = Logger.getLogger(StringUtility.class);

	// ����Ψһ��
	static long currentMill = 0;
	static List randlist = new ArrayList();

	/**
	 * �����null�ַ�, �򷵻�""�ַ�
	 * 
	 * @param str
	 *            String
	 * @return String
	 */
	public static String getClobContent(Clob content) {
		StringBuffer stringvalue = new StringBuffer(10000);
		try {
			BufferedReader reader = new BufferedReader(content.getCharacterStream());
			char[] temp = new char[50];
			String tmp = null;
			tmp = reader.readLine();
			while (tmp != null) {
				stringvalue.append(tmp);
				tmp = reader.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringvalue.toString();
	}

	public static String filterNull(String str) {
		if (str == null) {
			return new String();
		} else {
			return str;
		}
	}

	/**
	 * �����null����, �򷵻�""�ַ�
	 * 
	 * @param obj
	 *            Object
	 * @return String
	 */
	public static String filterNullObject(Object obj) {
		if (obj == null) {
			return new String();
		} else {
			return obj.toString();
		}
	}

	/**
	 * ���ַ�str�г��ֵ�oldStr�ַ��滻ΪnewStr�ַ�
	 * 
	 * @param str
	 *            Ҫ�����滻�������ַ�
	 * @param oldStr
	 *            ԭ�ַ�
	 * @param newStr
	 *            �滻�ַ�
	 * @return �滻�Ժ���ַ�
	 */
	public static String replace(String str, String oldStr, String newStr) {
		String string = str;
		StringBuffer buf = new StringBuffer();
		int index = 0;
		int idx = string.indexOf(oldStr);
		if (idx == -1)
			buf.append(string);
		while (idx != -1) {
			buf.append(string.substring(index, idx)).append(newStr);
			index = idx + oldStr.length();
			idx = string.indexOf(oldStr, index);
			if (idx == -1)
				buf.append(string.substring(index, string.length()));
		}

		return buf.toString();
	}

	/**
	 * �����ַ��еķǷ��ַ�
	 * 
	 * @param in
	 * @return String
	 */
	public static String escapeHTMLTags(String in) {
		if (in == null || in.length() <= 0) {
			return "";
		}
		// ע�⣬������_temp1ĩβ���ת���ַ�ͬʱҲҪ��_temp2ĩβ����滻�ַ�����Ҫ��"|"�ָ�
		// ����ASCII�����127���ַ�����ӵ�_temp��ʱ��Ҫ��escape��ʽ��ʾ���磺\u00FF��ʾASCII��Ϊ255���ַ�
		String _temp1 = "\u0026\u00A9\u00AE\u2122\"\u003C\u003E";
		String _temp2 = "&amp;|&copy;|&reg;|&trade;|&quot;|&lt;|&gt;";

		// Init vars.
		StringBuffer ret = new StringBuffer();
		String _new = "";

		// �Ƚ�in�к��е�ת�����ַ�ת����һ���ת��ǰ�ַ�����ͻ���ȷת��һ���ַ�
		// ���ǻὫ�û�����İ���_temp2�е�ת�����ַ�������Ӧ��ԭ�ַ�
		// ���磬�û�����" "����ת����"&nbsp;"����������û�����"&nbsp;"�ͻᱻ��Ϊ��" ".
		int i;
		StringTokenizer st1 = new StringTokenizer(_temp2, "|");
		ArrayList escapeArray = new ArrayList();
		while (st1.hasMoreTokens()) {
			String strToken = st1.nextToken();
			escapeArray.add(strToken);
		}
		/*
		 * for (i = 0; st1.hasMoreTokens(); i++) { in = replace(in,
		 * st1.nextToken(), _temp1.substring(i, i + 1)); }
		 */
		// �����ַ�ת��
		int _length = in.length();
		for (i = 0; i < _length; i++) {
			char t_char = in.charAt(i);
			int _index = _temp1.indexOf(t_char);
			if (_index < 0) {
				// �����ڴ�ת���ַ�
				ret.append(t_char);
			} else {
				// ���ڴ�ת���ַ�_temp2��ȥ������Ӧ���滻�ַ�
				_new = (String) escapeArray.get(_index);
				ret.append(_new);
			}
		}
		return ret.toString();
	}

	public static String filterHtml(Object oIn) {
		// ����ǿն��󣬷��ؿ��ַ�
		if (oIn == null) {
			return "";
		}
		String strIn = oIn.toString();
		return escapeHTMLTags(strIn);
	}

	/**
	 * ���� ����
	 * 
	 * @param toBeProcessed
	 *            String
	 * @param mode
	 *            0 - ���ܣ����� - ����
	 * @return String
	 */
	public static String encrypt(String toBeProcessed, int mode) {
		String ret = new String();
		// ����ǿ�ֱ�ӷ���
		if (toBeProcessed == null || toBeProcessed.length() <= 0) {
			return ret;
		}
		// ����
		int len = toBeProcessed.length();
		int i, j, k, l;
		if (mode == 0) { // ����
			j = (int) (16 * Math.random() - 16);
			for (i = 1; i <= len; i++) {
				k = j + i % 4;
				ret = String.valueOf((char) (toBeProcessed.charAt(i - 1) + k)) + ret;
			}
			ret += String.valueOf((char) (j + 70));
		} else { // ����
			l = len - 1;
			j = toBeProcessed.charAt(len - 1) - 70;
			for (i = 1; i <= l; i++) {
				k = j + i % 4;
				ret += String.valueOf((char) (toBeProcessed.charAt(l - i) - k));
			}
		}
		return ret;
	}

	/**
	 * �����ַ��еĵ���� һ������ű�Ϊ��������� Liang Yong, Feb 28, 2003
	 * 
	 * @return java.lang.String
	 * @param oldValue
	 *            java.lang.String
	 */
	public static String processSingleQuote(String oldValue) {
		// Process single quotes
		String newValue = new String();
		if (oldValue != null) {
			char c;
			for (int i = 0; i < oldValue.length(); i++) {
				c = oldValue.charAt(i);
				if (c == '\'') {
					newValue += c;
				}
				newValue += c;
			}
		}
		return newValue;
	}

	// ��Ҫ���������js�ַ����������htmlҳ��ʱ����js����
	// ע�⣬��\\�����������ǰ�ˣ���ΪҪ�������������ַ�ʱ��Ҫ�õ���
	private final static String[] jsSpecialChars = { "\\", "\'", "\"" };

	/**
	 * �������js�е������ַ����ת�崦�� �磬��\'ת����\\\'
	 * 
	 * @param in
	 * @return
	 */
	public static String escapeJSSpecialChar(String in) {
		String ret = new String();
		if (in == null || in.length() <= 0) {
			return ret;
		}

		// ��in�е�js specail charȫ������\
		ret = in;
		for (int i = 0; i < jsSpecialChars.length; i++) {
			ret = replace(ret, jsSpecialChars[i], "\\" + jsSpecialChars[i]);
		}
		return ret;
	}

	// ��λ�ַ�
	public static final char[] ascchars = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '`', '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '=', '+', '|', '\\', '[', ']', '{', '}', '\'', '\"', ';', ':', ',', '.',
			'<', '>', '/', '?' };

	/**
	 * ��ý�ȡ��ָ�����Ⱥ���ַ����������ָ���ַ���档
	 * 
	 * @param str
	 *            ��Ҫ�޶����ȵ��ַ�
	 * @param length
	 *            ��ȡλ��,��������λ
	 * @param more
	 *            �и��������滻�ַ�
	 * @return ��ȡ����ַ�
	 */
	public static String getMoreString(String str, int length, String more) {
		if (str == null || str.equals(""))
			return "";
		else {
			int len = str.length();
			int curLength = 0;
			StringBuffer buf = new StringBuffer();
			boolean isSingleChar;
			boolean hasMore = false;
			for (int i = 0; i < len; i++) {
				isSingleChar = false;
				if (curLength < length) {
					for (int j = 0; j < ascchars.length; j++) {
						if (str.charAt(i) == ascchars[j]) {
							buf.append(str.charAt(i));
							curLength++;
							isSingleChar = true;
							break;
						}
					}
					if (!isSingleChar) {
						buf.append(str.charAt(i));
						curLength += 2;
					}
				} else
					hasMore = true;
			}
			if (more == null) {
				more = "...";
			}
			if (hasMore)
				return buf.append(more).toString();
			else
				return buf.toString();
		}
	}

	public static String getNextLevel(String maxLevel, int level) {
		String ParentLevel = maxLevel.substring(0, maxLevel.length() - 3);
		String curr = maxLevel.substring(maxLevel.length() - 3, maxLevel.length());
		int iNext = Integer.parseInt(curr) + 1;
		return ParentLevel + leftPad(iNext, level, '0');
	}

	/**
	 * ������ת��Ϊ�ַ�����ʽ��Ϊָ���ĳ��ȣ�����λ���ǰ�油ָ���ַ� Input: 1, 5, '0' Output:
	 * "00001"
	 * 
	 * @return java.lang.String
	 * @param value
	 *            - Ҫת��������
	 * @param number
	 *            - Ҫ�����λ��
	 * @param c
	 *            char - ������ַ�
	 */
	public static String leftPad(int value, int number, char c) {
		String s = "";
		String temp = (new Integer(value)).toString();
		if (temp.length() <= number) {
			for (int i = 0; i < number; i++) {
				s += c;
			}
			s = s.substring(0, number - temp.length()) + temp;
		} else {
			logger.info("Error: " + number + "'s length is " + temp.length() + ", it's bigger than " + number + ".");
		}
		return s;
	}

	/**
	 * ������һ��ָ�����ȵ������ַ�
	 * 
	 * @return java.lang.String
	 * @param int num - Ҫ��ɵ��ַ���
	 */
	public static String random(int num) {
		String ret = "";
		for (int i = 0; i < num; i++) {
			int randomInt = (int) ((java.lang.Math.random()) * 10);
			ret = ret.concat(Integer.toString(randomInt));
		}
		return ret;
	}

	/**
	 * ���ַ��еõ��ļ���
	 * 
	 * @return java.lang.String
	 * @param src
	 *            java.lang.String
	 */
	public static String getFileName(String str) {
		int idx = str.lastIndexOf("/") + 1;
		if (idx == 0)
			return "";
		return str.substring(idx);
	}

	/**
	 * ���ַ��еõ��ļ�·��
	 * 
	 * @return java.lang.String
	 * @param src
	 *            java.lang.String
	 */
	public static String getFileDir(String str) {
		int idx = str.lastIndexOf("/");
		if (idx == 0)
			return "";
		return str.substring(0, idx);
	}

	/**
	 * �ѷָ���ַ�ϳ�����
	 * 
	 * @param src
	 * @param delim
	 * @return
	 */
	public static String[] split(String src, String delim) {
		if (src == null || delim == null)
			return null;
		StringTokenizer st = new StringTokenizer(src, delim);
		Vector vct = new Vector();
		for (; st.hasMoreTokens(); vct.add(st.nextToken()))
			;
		Object tks[] = vct.toArray();
		String rt[] = new String[vct.size()];
		System.arraycopy(((Object) (tks)), 0, rt, 0, vct.size());
		return rt;
	}

	/**
	 * ����ָ�����ȵ�����ַ�
	 * 
	 * @param i
	 * @return String
	 */
	public static String randomString(int i) {
		Random randGen = new Random();
		char numbersAndLetters[] = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

		if (i < 1)
			return null;
		char ac[] = new char[i];
		for (int j = 0; j < ac.length; j++)
			ac[j] = numbersAndLetters[randGen.nextInt(61)];

		return new String(ac);
	}

	public static String getUniqueID() {
		long tempMill = System.currentTimeMillis();
		String randstr = String.valueOf((int) (10000 * Math.random()));
		synchronized (randlist) {
			if (tempMill > currentMill) {
				currentMill = tempMill;
				randlist.clear();
			} else {
				while (randlist.contains(randstr)) {
					randstr = String.valueOf((int) (10000 * Math.random()));
				}
				randlist.add(randstr);
			}
		}
		String id = tempMill + "_" + randstr;
		return id;
	}

	public static void main(String args[]) {
		String str = "����abc��hehe";
		logger.info("getMoreString( \"" + str + "\", 7, \"...\" ) = " + getMoreString(str, 7, "..."));
		logger.info("getMoreString( \"" + str + "\", 8, \"...\" ) = " + getMoreString(str, 8, "..."));
		logger.info("getMoreString( \"" + str + "\", 9, \"...\" ) = " + getMoreString(str, 9, "..."));
		logger.info("getMoreString( \"" + str + "\", 10, \"...\" ) = " + getMoreString(str, 10, "..."));
		logger.info("getMoreString( \"" + str + "\", 50, \"...\" ) = " + getMoreString(str, 50, "..."));

		// Test random()
		logger.info("the method random(10)=" + StringUtility.random(10));
		logger.info(String.valueOf(StringUtility.split("", ",").length));
	}
}