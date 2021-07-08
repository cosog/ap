package com.cosog.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.BreakIterator;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * <p>
 * Title: Science Management Project
 * </p>
 * <p>
 * Description: Science Management Project
 * </p>
 * <p>
 * Created on 2008-8-26
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: JamieSoft
 * </p>
 * 
 * @author Jamie Xu
 * @version 1.0
 */

public class StringUtil {

	/**
	 * StringUtil constructor comment.
	 */
	public StringUtil() {
		super();
	}

	/**
	 * ת����ݿ�ͨ���ַ�'%','_' Creation date: (2000-12-20 14:49:55)
	 * 
	 * @return java.lang.String
	 * @param src
	 *            java.lang.String
	 */
	public static String convertCastChar(String src) {
		if (src == null || src.equals("")) {
			return src;
		}
		int length = src.length();
		StringBuffer tmp = new StringBuffer();
		for (int i = 0; i < length; i++) {
			switch (src.charAt(i)) {
			case '%':
			case '_':
			case '\\':
				tmp.append("\\");
				break;
			}
			tmp.append(src.charAt(i));
		}
		return tmp.toString();
	}

	/**
	 * ת�������Html��� Creation date: (2000-12-20 14:58:19)
	 * 
	 * @return java.lang.String
	 * @param src
	 *            java.lang.String
	 */
	public static String convertForHtml(String src) {
		if (src == null || src.equals("")) {
			return src;
		}
		int length = src.length();
		StringBuffer tmp = new StringBuffer();
		for (int i = 0; i < length; i++) {
			switch (src.charAt(i)) {
			case '<':
				tmp.append("&lt;");
				break;
			case '>':
				tmp.append("&gt;");
				break;
			case '"':
				tmp.append("&quot;");
				break;
			case ' ': {
				int spaceCount = 0;
				for (; src.charAt(i) == ' '; i++, spaceCount++)
					;
				for (int j = 0; j < spaceCount / 2; j++) {
					tmp.append("��");
				}
				if (spaceCount % 2 != 0) {
					tmp.append("&#160;");
				}
				--i;
				break;
			}
			case '\n':
				tmp.append("<br/>");
				break;
			case '&':
				tmp.append("&amp;");
				break;
			case '\r':
				break;
			default:
				tmp.append(src.charAt(i));
				break;
			}
		}
		return tmp.toString();
	}

	/**
	 * Insert the method's description here. Creation date: (2001-6-6 8:43:25)
	 * 
	 * @return java.lang.String
	 * @param srv
	 *            java.lang.String
	 */
	public static String convertForXml(String src) {
		if (src == null || src.equals("")) {
			return src;
		}
		int length = src.length();
		StringBuffer tmp = new StringBuffer();
		for (int i = 0; i < length; i++) {
			switch (src.charAt(i)) {
			case '<':
				tmp.append("&lt;");
				break;
			case '>':
				tmp.append("&gt;");
				break;
			case '"':
				tmp.append("&quot;");
				break;
			case ' ': {
				int spaceCount = 0;
				for (; src.charAt(i) == ' '; i++, spaceCount++)
					;
				for (int j = 0; j < spaceCount / 2; j++) {
					tmp.append("��");
				}
				if (spaceCount % 2 != 0) {
					tmp.append("&#160;");
				}
				--i;
				break;
			}
			case '&':
				tmp.append("&amp;");
				break;
			case '\r':
				break;
			default:
				tmp.append(src.charAt(i));
				break;
			}
		}
		return tmp.toString();
	}

	/**
	 * Insert the method's description here. Creation date: (2001-6-6 8:43:25)
	 * 
	 * @return java.lang.String
	 * @param srv
	 *            java.lang.String
	 */
	public static String convertForXml2(String src) {
		if (src == null || src.equals("")) {
			return src;
		}
		int length = src.length();
		StringBuffer tmp = new StringBuffer();
		for (int i = 0; i < length; i++) {
			switch (src.charAt(i)) {
			case ' ': {
				int spaceCount = 0;
				for (; i < src.length() && src.charAt(i) == ' '; i++, spaceCount++)
					;
				for (int j = 0; j < spaceCount / 2; j++) {
					tmp.append("��");
				}
				if (spaceCount % 2 != 0) {
					tmp.append(" ");
				}
				--i;
				break;
			}
			default:
				tmp.append(src.charAt(i));
				break;
			}
		}
		return tmp.toString();
	}

	/**
	 * ת�嵥��� Creation date: (2000-12-20 14:43:29)
	 * 
	 * @return java.lang.String
	 * @param src
	 *            java.lang.String
	 */
	public static String convertSingleQuot(String src) {
		if (src == null || src.equals("")) {
			return src;
		}
		int length = src.length();
		StringBuffer tmp = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (src.charAt(i) == '\'') {
				tmp.append("\'\'");
			} else {
				tmp.append(src.charAt(i));
			}
		}
		return tmp.toString();
	}

	/**
	 * �Ӵ��滻 Creation date: (2000-12-23 15:32:25)
	 * 
	 * @return java.lang.String
	 * @param src
	 *            java.lang.String
	 * @param mod
	 *            java.lang.String
	 * @param str
	 *            java.lang.String
	 */
	public static String replace(String src, String mod, String str) {
		if (src == null || src.length() == 0) {
			return src;
		}
		if (mod == null || mod.length() == 0) {
			return src;
		}
		if (src == null) {
			src = "";
		}
		StringBuffer buffer = new StringBuffer();
		int idx1 = 0;
		int idx2 = 0;
		while ((idx2 = src.indexOf(mod, idx1)) != -1) {
			buffer.append(src.substring(idx1, idx2)).append(str);
			idx1 = idx2 + mod.length();
		}
		buffer.append(src.substring(idx1));
		return buffer.toString();
	}

	/**
	 * Insert the method's description here. Creation date: (2001-5-30 13:46:08)
	 * 
	 * @return java.lang.String[]
	 */
	public static String[] split(String src, String delim) {
		if (src == null || delim == null) {
			return null;
		}
		StringTokenizer st = new StringTokenizer(src, delim);
		Vector vct = new Vector();
		while (st.hasMoreTokens()) {
			vct.add(st.nextToken());
		}
		Object[] tks = vct.toArray();
		String rt[] = new String[vct.size()];
		System.arraycopy(tks, 0, rt, 0, vct.size());
		return rt;
	}

	/**
	 * Method declaration
	 * 
	 * @return
	 * @see
	 */
	public static String getCurrentTimeStamp() {
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.LONG, new Locale("zh", "CN"));
		return (df.format(new java.util.Date(System.currentTimeMillis())));
	}

	public static final String replaceIgnoreCase(String line, String oldString, String newString) {
		if (line == null)
			return null;
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			char line2[] = line.toCharArray();
			char newString2[] = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j;
			for (j = i; (i = lcLine.indexOf(lcOldString, i)) > 0; j = i) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
			}

			buf.append(line2, j, line2.length - j);
			return buf.toString();
		} else {
			return line;
		}
	}

	public static final String replaceIgnoreCase(String line, String oldString, String newString, int count[]) {
		if (line == null)
			return null;
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			int counter = 0;
			char line2[] = line.toCharArray();
			char newString2[] = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j;
			for (j = i; (i = lcLine.indexOf(lcOldString, i)) > 0; j = i) {
				counter++;
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
			}

			buf.append(line2, j, line2.length - j);
			count[0] = counter;
			return buf.toString();
		} else {
			return line;
		}
	}

	public static final String replace(String line, String oldString, String newString, int count[]) {
		if (line == null)
			return null;
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			int counter = 0;
			counter++;
			char line2[] = line.toCharArray();
			char newString2[] = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j;
			for (j = i; (i = line.indexOf(oldString, i)) > 0; j = i) {
				counter++;
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
			}

			buf.append(line2, j, line2.length - j);
			count[0] = counter;
			return buf.toString();
		} else {
			return line;
		}
	}

	public static final String escapeHTMLTags(String in) {
		if (in == null)
			return null;
		int i = 0;
		int last = 0;
		char input[] = in.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer((int) ((double) len * 1.3D));
		for (; i < len; i++) {
			char ch = input[i];
			if (ch <= '>')
				if (ch == '<') {
					if (i > last)
						out.append(input, last, i - last);
					last = i + 1;
					out.append(LT_ENCODE);
				} else if (ch == '>') {
					if (i > last)
						out.append(input, last, i - last);
					last = i + 1;
					out.append(GT_ENCODE);
				}
		}

		if (last == 0)
			return in;
		if (i > last)
			out.append(input, last, i - last);
		return out.toString();
	}

	public static final String escapeNull(String str) {
		return str == null ? "" : str;
	}

	public static final synchronized String hash(String data) {
		if (digest == null)
			try {
				digest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException nsae) {
				System.err.println("Failed to load the MD5 MessageDigest. Jive will be unable to function normally.");
				nsae.printStackTrace();
			}
		digest.update(data.getBytes());
		return encodeHex(digest.digest());
	}

	public static final String encodeHex(byte bytes[]) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if ((bytes[i] & 0xff) < 16)
				buf.append("0");
			buf.append(Long.toString(bytes[i] & 0xff, 16));
		}

		return buf.toString();
	}

	public static final byte[] decodeHex(String hex) {
		char chars[] = hex.toCharArray();
		byte bytes[] = new byte[chars.length / 2];
		int byteCount = 0;
		for (int i = 0; i < chars.length; i += 2) {
			byte newByte = 0;
			newByte |= hexCharToByte(chars[i]);
			newByte <<= 4;
			newByte |= hexCharToByte(chars[i + 1]);
			bytes[byteCount] = newByte;
			byteCount++;
		}

		return bytes;
	}

	private static final byte hexCharToByte(char ch) {
		switch (ch) {
		case 48: // '0'
			return 0;

		case 49: // '1'
			return 1;

		case 50: // '2'
			return 2;

		case 51: // '3'
			return 3;

		case 52: // '4'
			return 4;

		case 53: // '5'
			return 5;

		case 54: // '6'
			return 6;

		case 55: // '7'
			return 7;

		case 56: // '8'
			return 8;

		case 57: // '9'
			return 9;

		case 97: // 'a'
			return 10;

		case 98: // 'b'
			return 11;

		case 99: // 'c'
			return 12;

		case 100: // 'd'
			return 13;

		case 101: // 'e'
			return 14;

		case 102: // 'f'
			return 15;

		case 58: // ':'
		case 59: // ';'
		case 60: // '<'
		case 61: // '='
		case 62: // '>'
		case 63: // '?'
		case 64: // '@'
		case 65: // 'A'
		case 66: // 'B'
		case 67: // 'C'
		case 68: // 'D'
		case 69: // 'E'
		case 70: // 'F'
		case 71: // 'G'
		case 72: // 'H'
		case 73: // 'I'
		case 74: // 'J'
		case 75: // 'K'
		case 76: // 'L'
		case 77: // 'M'
		case 78: // 'N'
		case 79: // 'O'
		case 80: // 'P'
		case 81: // 'Q'
		case 82: // 'R'
		case 83: // 'S'
		case 84: // 'T'
		case 85: // 'U'
		case 86: // 'V'
		case 87: // 'W'
		case 88: // 'X'
		case 89: // 'Y'
		case 90: // 'Z'
		case 91: // '['
		case 92: // '\\'
		case 93: // ']'
		case 94: // '^'
		case 95: // '_'
		case 96: // '`'
		default:
			return 0;
		}
	}

	public static String encodeBase64(String data) {
		return encodeBase64(data.getBytes());
	}

	public static String encodeBase64(byte data[]) {
		int len = data.length;
		StringBuffer ret = new StringBuffer((len / 3 + 1) * 4);
		for (int i = 0; i < len; i++) {
			int c = data[i] >> 2 & 0x3f;
			ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
			c = data[i] << 4 & 0x3f;
			if (++i < len)
				c |= data[i] >> 4 & 0xf;
			ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
			if (i < len) {
				c = data[i] << 2 & 0x3f;
				if (++i < len)
					c |= data[i] >> 6 & 3;
				ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
			} else {
				i++;
				ret.append('=');
			}
			if (i < len) {
				c = data[i] & 0x3f;
				ret.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(c));
			} else {
				ret.append('=');
			}
		}

		return ret.toString();
	}

	public static String decodeBase64(String data) {
		return decodeBase64(data.getBytes());
	}

	public static String decodeBase64(byte data[]) {
		int len = data.length;
		StringBuffer ret = new StringBuffer((len * 3) / 4);
		for (int i = 0; i < len; i++) {
			int c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(data[i]);
			i++;
			int c1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf(data[i]);
			c = c << 2 | c1 >> 4 & 3;
			ret.append((char) c);
			if (++i < len) {
				c = data[i];
				if (61 == c)
					break;
				c = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf((char) c);
				c1 = c1 << 4 & 0xf0 | c >> 2 & 0xf;
				ret.append((char) c1);
			}
			if (++i >= len)
				continue;
			c1 = data[i];
			if (61 == c1)
				break;
			c1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".indexOf((char) c1);
			c = c << 6 & 0xc0 | c1;
			ret.append((char) c);
		}

		return ret.toString();
	}

	public static final String[] toLowerCaseWordArray(String text) {
		if (text == null || text.length() == 0)
			return new String[0];
		ArrayList wordList = new ArrayList();
		BreakIterator boundary = BreakIterator.getWordInstance();
		boundary.setText(text);
		int start = 0;
		for (int end = boundary.next(); end != -1; end = boundary.next()) {
			String tmp = text.substring(start, end).trim();
			tmp = replace(tmp, "+", "");
			tmp = replace(tmp, "/", "");
			tmp = replace(tmp, "\\", "");
			tmp = replace(tmp, "#", "");
			tmp = replace(tmp, "*", "");
			tmp = replace(tmp, ")", "");
			tmp = replace(tmp, "(", "");
			tmp = replace(tmp, "&", "");
			if (tmp.length() > 0)
				wordList.add(tmp);
			start = end;
		}

		return (String[]) wordList.toArray(new String[wordList.size()]);
	}

	public static final String randomString(int length) {
		if (length < 1)
			return null;
		char randBuffer[] = new char[length];
		for (int i = 0; i < randBuffer.length; i++)
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];

		return new String(randBuffer);
	}

	public static final String chopAtWord(String string, int length) {
		if (string == null)
			return string;
		char charArray[] = string.toCharArray();
		int sLength = string.length();
		if (length < sLength)
			sLength = length;
		for (int i = 0; i < sLength - 1; i++) {
			if (charArray[i] == '\r' && charArray[i + 1] == '\n')
				return string.substring(0, i + 1);
			if (charArray[i] == '\n')
				return string.substring(0, i);
		}

		if (charArray[sLength - 1] == '\n')
			return string.substring(0, sLength - 1);
		if (string.length() < length)
			return string;
		for (int i = length - 1; i > 0; i--)
			if (charArray[i] == ' ')
				return string.substring(0, i).trim();

		return string.substring(0, length);
	}

	public static final String escapeForXML(String string) {
		if (string == null)
			return null;
		int i = 0;
		int last = 0;
		char input[] = string.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer((int) ((double) len * 1.3D));
		for (; i < len; i++) {
			char ch = input[i];
			if (ch <= '>')
				if (ch == '<') {
					if (i > last)
						out.append(input, last, i - last);
					last = i + 1;
					out.append(LT_ENCODE);
				} else if (ch == '&') {
					if (i > last)
						out.append(input, last, i - last);
					last = i + 1;
					out.append(AMP_ENCODE);
				} else if (ch == '"') {
					if (i > last)
						out.append(input, last, i - last);
					last = i + 1;
					out.append(QUOTE_ENCODE);
				}
		}

		if (last == 0)
			return string;
		if (i > last)
			out.append(input, last, i - last);
		return out.toString();
	}

	public static final String unescapeFromXML(String string) {
		string = replace(string, "&lt;", "<");
		string = replace(string, "&gt;", ">");
		string = replace(string, "&quot;", "\"");
		return replace(string, "&amp;", "&");
	}

	public static final String zeroPadString(String string, int length) {
		if (string == null || string.length() > length) {
			return string;
		} else {
			StringBuffer buf = new StringBuffer(length);
			buf.append(zeroArray, 0, length - string.length()).append(string);
			return buf.toString();
		}
	}

	public static final String dateToMillis(Date date) {
		return zeroPadString(Long.toString(date.getTime()), 15);
	}

	/**
	 * ��ʽ�����»���'_'�����'-'�������ַ��磺 "user-name", ���Ը�ʽ��Ϊ"userName"
	 * 
	 * @return java.lang.String
	 * @param name
	 *            java.lang.String
	 * @param firstCharUpperCase
	 *            boolean ���Ϊtrue����ô���ص�����ĸ��д����֮��Сд
	 */
	public static final String formatJavaName(String name, boolean firstCharUpperCase) {
		if (name == null || name.length() <= 1)
			return name;
		StringTokenizer tokenizer = new StringTokenizer(name, "-_");
		StringBuffer tmp = new StringBuffer();
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			tmp.append(Character.toUpperCase(token.charAt(0))).append(token.substring(1));
		}
		if (!firstCharUpperCase) {
			String ch = String.valueOf(Character.toLowerCase(tmp.charAt(0)));
			tmp.replace(0, 1, ch);
		}
		return tmp.toString();
	}

	public static final String getGetMethodName(String name) {
		return "get" + formatJavaName(name, true);
	}

	public static final String getSetMethodName(String name) {
		return "set" + formatJavaName(name, true);
	}

	public static final String addSingleQuote(String str) {
		if (str == null)
			return null;
		String[] strarray = StringUtil.split(str, ",");
		String result = "";
		for (int i = 0; i < strarray.length; i++) {
			if (i == 0)
				result = "'" + strarray[i] + "'";
			else
				result = result + ",'" + strarray[i] + "'";
		}
		return result;
	}

	/**
	 * ��ʽ�����»���'_'�����'-'�������ַ�����ĸСд���磺 "user-name",
	 * ���Ը�ʽ��Ϊ"userName"
	 * 
	 * @return java.lang.String
	 * @param name
	 *            java.lang.String
	 */
	public static final String formatJavaName(String name) {
		return formatJavaName(name, false);
	}

	public static String getCurrentDay() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		String daystr = "";
		if (month < 10)
			daystr = year + "-0" + month;
		else
			daystr = year + "-" + month;
		if (day < 10)
			daystr = daystr + "-0" + day;
		else
			daystr = daystr + "-" + day;

		return daystr;
	}

	public static String getDayLastYear() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR) - 1;
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		String daystr = "";
		if (month < 10)
			daystr = year + "-0" + month;
		else
			daystr = year + "-" + month;
		if (day < 10)
			daystr = daystr + "-0" + day;
		else
			daystr = daystr + "-" + day;

		return daystr;
	}

	/**
	 * method ���ַ����͵�����ת��Ϊһ��timestamp��ʱ�����java.sql.Timestamp��
	 * 
	 * @param dateString
	 *            ��Ҫת��Ϊtimestamp���ַ�
	 * @return dataTime timestamp
	 */
	public final static java.sql.Timestamp string2Time(String dateString) throws java.text.ParseException {
		DateFormat dateFormat;
		dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS", Locale.ENGLISH);// �趨��ʽ
		// dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss",
		// Locale.ENGLISH);
		dateFormat.setLenient(false);
		java.sql.Timestamp dateTime = new java.sql.Timestamp(dateFormat.parse(dateString).getTime());// Timestamp����,timeDate.getTime()����һ��long��
		return dateTime;
	}

	/**
	 * method ���ַ����͵�����ת��Ϊһ��Date��java.sql.Date��
	 * 
	 * @param dateString
	 *            ��Ҫת��ΪDate���ַ�
	 * @return dataTime Date
	 */
	public final static java.sql.Date string2Date(String dateString) throws java.lang.Exception {
		DateFormat dateFormat;
		dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		dateFormat.setLenient(false);
		if (dateString == null || dateString.length() < 10)
			return null;
		java.util.Date timeDate = dateFormat.parse(dateString);// util����
		java.sql.Date dateTime = new java.sql.Date(timeDate.getTime());// sql����
		return dateTime;
	}

	public final static int diffDay(String dayString) throws ParseException {
		System.out.println(dayString);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sysDay = sdf.format(new Date()).toString();
		return diffDay(dayString, sysDay);
	}

	public final static int diffDay(String beginString, String endString) throws ParseException {
		long day = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date begin = sdf.parse(beginString);
		Date end = sdf.parse(endString);

		long between = (end.getTime() - begin.getTime()) / 1000;// ����1000��Ϊ��ת������

		day = between / (24 * 3600);

		return (int) day;
	}

	/**
	 * ��ҳ�������ж�ȡ�����ֵ��д�뵽hash����
	 * 
	 * @param condition
	 *            String
	 * 
	 * @return Map
	 */
	public static Map readCondition(String condition) {
		Map<String, String> ret = new HashMap<String, String>();
		if ((condition != null) && (!(condition.equals("")))) {
			String[] condArray = condition.split("&");
			for (int i = 0; i < condArray.length; i++) {
				String[] pairstr = condArray[i].split("=");

				if (pairstr.length > 1)
					ret.put(pairstr[0], pairstr[1]);
				else
					ret.put(pairstr[0], "");
			}
		}
		return ret;
	}

	/**
	 * ��֤���ڵĺϷ���
	 * 
	 * @param date
	 *            String
	 * 
	 * @return boolean
	 */
	public static boolean validateDate(String date) {
		boolean valid = true;
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.SIMPLIFIED_CHINESE);
		try {
			dateFormat.parse(date);
		} catch (ParseException e) {
			valid = false;
		}
		return valid;
	}

	/**
	 * ��֤���ڵĺϷ���
	 * 
	 * @param date
	 *            String
	 * 
	 * @return boolean
	 */
	public static boolean validateNumeric(String value, boolean dotflag) {
		boolean valid = true;
		String NumStr = "0123456789";

		for (int i = 0; i < value.length(); ++i) {
			char chr = value.charAt(i);
			if (NumStr.indexOf(chr, 0) == -1) {
				if (!(dotflag && chr == '.')) {
					valid = false;
					break;
				}
			}
		}

		return valid;
	}

	/**
	 * ��֤���ڵĺϷ���
	 * 
	 * @param date
	 *            String
	 * 
	 * @return boolean
	 */
	public static boolean validateInteger(String value) {
		boolean valid = true;

		char ch;

		return valid;
	}

	private static final char QUOTE_ENCODE[] = "&quot;".toCharArray();
	private static final char AMP_ENCODE[] = "&amp;".toCharArray();
	private static final char LT_ENCODE[] = "&lt;".toCharArray();
	private static final char GT_ENCODE[] = "&gt;".toCharArray();
	private static MessageDigest digest = null;
	private static final int fillchar = 61;
	private static final String cvt = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	private static Random randGen = new Random();
	private static char numbersAndLetters[] = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	private static final char zeroArray[] = "0000000000000000".toCharArray();
}
