package com.gao.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 通用日期处理工具类
 * @author 
 * 2012-2-3
 */
public class DateUtils {
	public static Date date = null;

	public static DateFormat dateFormat = null;

	public static Calendar calendar = null;
	//获取年
	public static int getYears() {
		Date date=new Date();
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		  int Year = calendar.get(Calendar.YEAR);   
		return Year;
	}
	
	
	//获取月
	public static int getMonths() {
		Date date=new Date();
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		  int month = calendar.get(Calendar.MONTH) + 1;   
		return month;
	}
	//获取天数
	public static int getDays() {
		Date date=new Date();
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		  int Days = calendar.get(Calendar.DAY_OF_MONTH);   
		return Days;
	}
	//获取周
	public static int getWeeks() {
		Date date=new Date();
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		  int WeeK = calendar.get(Calendar.DAY_OF_WEEK);   
		return WeeK;
	}
	public static int getSecond() {
		Date date=new Date();
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		  int SECOND = calendar.get(Calendar.SECOND); 
		  if(SECOND>10){
			  SECOND=SECOND%10;
		  }
		return SECOND;
	}
	/**
	 * 时分钞
	 * @return
	 */
	public static String getNumberDate() {
		Date date=new Date();
		String timeString = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("mmhhss"); 
			timeString = dateFormat.format(date);
			return timeString;
		} catch (Exception ex) {
			ex.getCause();
		}
		return timeString;	
	}
	/**
	 * 年月日时分钞
	 * @return
	 */
	public static String getDateString() {
		Date date=new Date();
		String timeString = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddmmhhss"); 
			timeString = dateFormat.format(date);
			return timeString;
		} catch (Exception ex) {
			ex.getCause();
		}
		return timeString;	
	}
	/**
	 * 功能描述：格式化日期
	 * 
	 * @param dateStr
	 *            String 字符型日期
	 * @param format
	 *            String 格式
	 * @return Date 日期
	 */
	public static Date parseDate(String dateStr, String format) {
		try {
			dateFormat = new SimpleDateFormat(format);
			String dt = dateStr.replaceAll("-", "/");
			if ((!dt.equals("")) && (dt.length() < format.length())) {
				dt += format.substring(dt.length()).replaceAll("[YyMmDdHhSs]",
						"0");
			}
			date = (Date) dateFormat.parse(dt);
		} catch (Exception e) {
		}
		return date;
	}

	/**
	 * 功能描述：格式化日期
	 * 
	 * @param dateStr
	 *            String 字符型日期：YYYY-MM-DD 格式
	 * @return Date
	 */
	public static Date parseDate(String dateStr) {
		return parseDate(dateStr, "yyyy/MM/dd");
	}

	/**
	 * 功能描述：格式化输出日期
	 * 
	 * @param date
	 *            Date 日期
	 * @param format
	 *            String 格式
	 * @return 返回字符型日期
	 */
	public static String format(Date date, String format) {
		String result = "";
		try {
			if (date != null) {
				dateFormat = new SimpleDateFormat(format);
				result = dateFormat.format(date);
			}
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 功能描述：
	 * 
	 * @param date
	 *            Date 日期
	 * @return
	 */
	public static String format(Date date) {
		return format(date, "yyyy/MM/dd");
	}
	public static Date getTime(){
		// 系统时间取得
		Date date = new Date();
		return date;
	}
	/**
	 * 功能描述：返回年份
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回年份
	 */
	public static int getYear(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 功能描述：返回月份
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回月份
	 */
	public static int getMonth(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 功能描述：返回日份
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回日份
	 */
	public static int getDay(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 功能描述：返回小时
	 * 
	 * @param date
	 *            日期
	 * @return 返回小时
	 */
	public static int getHour(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 功能描述：返回分钟
	 * 
	 * @param date
	 *            日期
	 * @return 返回分钟
	 */
	public static int getMinute(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * 返回秒钟
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回秒钟
	 */
	public static int getSecond(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * 功能描述：返回毫秒
	 * 
	 * @param date
	 *            日期
	 * @return 返回毫秒
	 */
	public static long getMillis(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}

	/**
	 * 功能描述：返回字符型日期
	 * 
	 * @param date
	 *            日期
	 * @return 返回字符型日期 yyyy/MM/dd 格式
	 */
	public static String getDate(Date date) {
		return format(date, "yyyy/MM/dd");
	}

	/**
	 * 功能描述：返回字符型时间
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回字符型时间 HH:mm:ss 格式
	 */
	public static String getTime(Date date) {
		return format(date, "HH:mm:ss");
	}

	/**
	 * 功能描述：返回字符型日期时间
	 * 
	 * @param date
	 *            Date 日期
	 * @return 返回字符型日期时间 yyyy/MM/dd HH:mm:ss 格式
	 */
	public static String getDateTime(Date date) {
		return format(date, "yyyy/MM/dd HH:mm:ss");
	}
	public static String getTodayTime() {
		Date date=new Date();
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}
	public static String getTodayTimeMillis() {
		Date date=new Date();
		return format(date, "HHmmss");
	}
	/**
	 * 功能描述：日期相加
	 * 
	 * @param date
	 *            Date 日期
	 * @param day
	 *            int 天数
	 * @return 返回相加后的日期
	 */
	public static Date addDate(Date date, int day) {
		calendar = Calendar.getInstance();
		long millis = getMillis(date) + ((long) day) * 24 * 3600 * 1000;
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

	/**
	 * 功能描述：日期相减
	 * 
	 * @param date
	 *            Date 日期
	 * @param date1
	 *            Date 日期
	 * @return 返回相减后的日期
	 */
	public static int diffDate(Date date, Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
	}

	/**
	 * 功能描述：取得指定月份的第一天
	 * 
	 * @param strdate
	 *            String 字符型日期
	 * @return String yyyy-MM-dd 格式
	 */
	public static String getMonthBegin(String strdate) {
		date = parseDate(strdate);
		return format(date, "yyyy-MM") + "-01";
	}

	/**
	 * 功能描述：取得指定月份的最后一天
	 * 
	 * @param strdate
	 *            String 字符型日期
	 * @return String 日期字符串 yyyy-MM-dd格式
	 */
	public static String getMonthEnd(String strdate) {
		date = parseDate(getMonthBegin(strdate));
		calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return formatDate(calendar.getTime());
	}

	/**
	 * 功能描述：常用的格式化日期
	 * 
	 * @param date
	 *            Date 日期
	 * @return String 日期字符串 yyyy-MM-dd格式
	 */
	public static String formatDate(Date date) {
		return formatDateByFormat(date, "yyyy-MM-dd");
	}

	/**
	 * 功能描述：以指定的格式来格式化日期
	 * 
	 * @param date
	 *            Date 日期
	 * @param format
	 *            String 格式
	 * @return String 日期字符串
	 */
	public static String formatDateByFormat(Date date, String format) {
		String result = "";
		if (date != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.format(date);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/** --------------发票项目使用日期工具类---------------*/
	
	/**
	 * @功能描述:将日期转换为字符串
	 * @param Date
	 * @return String
	 * @author 王传圣
	 */
	public static String parseDateToString(Date date)throws Exception{
		
		String timeString = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			timeString = dateFormat.format(date);
			return timeString;
		} catch (Exception ex) {
			ex.getCause();
		}
		return timeString;	
	}
	
	/**
	 * @功能描述:将日期按照指定的格式转换为字符串
	 * @param Date
	 * @return String
	 * @author 王传圣
	 */
	public static String parseDateToString(Date date,String formatString)throws Exception{
		
		String timeString = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat(formatString); 
			timeString = dateFormat.format(date);
			return timeString;
		} catch (Exception ex) {
			ex.getCause();
		}
		return timeString;	
	}
	
	/**
	 * @功能描述:将日期加N个小时
	 * @param Date date,int n
	 * @return Date
	 * @author 王传圣
	 */
	public static Date DateAddHour(Date date,int n) throws Exception{
		
		Date dateTemp = null;
		Calendar Cal = Calendar.getInstance();    
		try {			
			Cal.setTime(date);
			Cal.add(Calendar.HOUR, n);   
			dateTemp = Cal.getTime();
			return dateTemp;
		} catch (Exception ex) {
			ex.getCause();
		}
		return dateTemp;		
	}
	
	/**
	 * @功能描述:将日期加N天
	 * @param Date date,int n
	 * @return Date
	 * @author 王传圣
	 */
	public static Date dateAddDay(Date date,int n) throws Exception{
		
		Date dateTemp = null;
		Calendar Cal = Calendar.getInstance();    
		try {			
			Cal.setTime(date);
			Cal.add(Calendar.DAY_OF_MONTH, n);   
			dateTemp = Cal.getTime();
			return dateTemp;
		} catch (Exception ex) {
			ex.getCause();
		}
		return dateTemp;		
	}
	
	/**
	 * @功能描述:将字符串转换为日期
	 * @param String timeString,String formatString
	 * @return Date
	 * @author 王传圣
	 */
	public static Date parseStringToDate(String timeString,String formatString) throws Exception{
		
		Date date = null;
		try {
			if(null != timeString && !"".equals(timeString)){
				DateFormat format = new SimpleDateFormat(formatString); 			
				date = format.parse(timeString);
			}	
			return date;
		} catch (Exception ex) {
			ex.getCause();
		}
		return date;	
	}
	
	/**
	 * @功能描述：设置时分秒
	 * @param Date date,int n
	 * @return Date
	 * @author 王传圣
	 */
	public static Date setDateTime(Date date,int hour,int minute,int second) throws Exception{
		
		Date dateTemp = null;
		Calendar Cal = Calendar.getInstance();    
		try {			
			Cal.setTime(date);
			Cal.set(Calendar.HOUR_OF_DAY, hour);
			Cal.set(Calendar.MINUTE, minute);
			Cal.set(Calendar.SECOND, second);   
			dateTemp = Cal.getTime();
			return dateTemp;
		} catch (Exception ex) {
			ex.getCause();
		}
		return dateTemp;		
	}
	/**
	 * 给日期加天数
	 * @param date
	 * @param days
	 * @return
	 * @throws Exception
	 */
	public static java.util.Date addTimeByDay(java.util.Date date,int days) throws Exception
	{
		Calendar calendar=Calendar.getInstance();   
		 calendar.setTime(date);
		calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)+days);
		return calendar.getTime();
	}
	/**
	 * 给日期加分钟
	 * @param date
	 * @param minutes
	 * @return
	 * @throws Exception
	 */
	public static java.util.Date addTimeByMinutes(java.util.Date date,int minutes) throws Exception
	{
		Calendar calendar=Calendar.getInstance();   
		 calendar.setTime(date);
		 calendar.set(Calendar.MINUTE,calendar.get(Calendar.MINUTE)+minutes);
		return calendar.getTime();
	}
	/**
	 * 添加秒
	 * @param date
	 * @param seconds
	 * @return
	 * @throws Exception
	 */
	public static java.util.Date addTimeBySeconds(java.util.Date date,int seconds) throws Exception
	{
		Calendar calendar=Calendar.getInstance();   
		 calendar.setTime(date);
		 calendar.set(Calendar.SECOND,calendar.get(Calendar.SECOND)+seconds);
		return calendar.getTime();
	}
	public static java.util.Date nowTime() throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String datestr = sdf.format(java.util.Calendar.getInstance().getTime());
		
		return sdf.parse(datestr);
	}
	public static java.util.Date nowFullTime() throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datestr = sdf.format(java.util.Calendar.getInstance().getTime());
		return sdf.parse(datestr);
	}
	public static java.util.Date nowFullTime(String format) throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String datestr = sdf.format(java.util.Calendar.getInstance().getTime());
		return sdf.parse(datestr);
	}
	public static String convertDateStrToString(String datestr,String format) throws Exception
	{
		String result = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try
		{
			result = sdf.format(sdf.parse(datestr));
		}
		catch (Exception ex)
		{
			 sdf = new SimpleDateFormat("yyyy-MM-dd");
			result = sdf.format(sdf.parse(datestr));
		}
		return result;
	}
	public static String convertDateToString(java.util.Date date,String format) throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	public static java.util.Date formatDateStr(String datestr) throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		return sdf.parse(datestr);
	}
	
	public static java.util.Date formatDateStr(String datestr,String format) throws Exception
	{
		java.util.Date result = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try
		{
			result = sdf.parse(datestr);
		}
		catch (Exception ex)
		{
			 sdf = new SimpleDateFormat("yyyy-MM-dd");
			result = sdf.parse(datestr);
		}
		return result;
	}
	public static java.util.Date formatFullDateStr(String datestr) throws Exception
	{
		java.util.Date result = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
			result = sdf.parse(datestr);
		}
		catch (Exception ex)
		{
			 sdf = new SimpleDateFormat("yyyy-MM-dd");
			result = sdf.parse(datestr);
		}
		return result;
	}
	/**
	 * 得到小时，分的格式例如 02:32
	 */
	public static final String getFormatTime(java.util.Date date) {
		String Tempstr;
		int i;
		SimpleDateFormat mydate = new SimpleDateFormat(" MMM dd H mm ss,yyyy");
		Tempstr = "";

		mydate.applyPattern("H");
		i = Integer.parseInt(mydate.format(date));
		if (i < 10) {
			Tempstr = Tempstr + "0" + String.valueOf(i) + ":";
		} else {
			Tempstr = Tempstr + String.valueOf(i) + ":";
		}

		mydate.applyPattern("mm");
		i = Integer.parseInt(mydate.format(date));
		if (i < 10) {
			Tempstr = Tempstr + "0" + String.valueOf(i);
		} else {
			Tempstr = Tempstr + String.valueOf(i);
		}

		return Tempstr;
	}

	/**
	 * 得到小时的格式例如 02
	 */
	public static final String getFormatHour(java.util.Date date) {
		String Tempstr;
		int i;
		SimpleDateFormat mydate = new SimpleDateFormat(" MMM dd H mm ss,yyyy");
		Tempstr = "";

		mydate.applyPattern("H");
		i = Integer.parseInt(mydate.format(date));
		Tempstr = Tempstr + String.valueOf(i);
		return Tempstr;
	}

	/**
	 * 
	 * 得到小时的格式例如 02
	 */
	public static final String getFormatMinute(Date date) {

		String Tempstr;
		int i;
		SimpleDateFormat mydate = new SimpleDateFormat(" MMM dd H mm ss,yyyy");
		Tempstr = "";

		mydate.applyPattern("mm");
		i = Integer.parseInt(mydate.format(date));
		Tempstr = Tempstr + String.valueOf(i);
		return Tempstr;
	}
	/**
	 * 根据日期得到年份字符串
	 */
	public static final String getYearFromDate(Date date) {
		String Tempstr;
		int i;
		SimpleDateFormat mydate = new SimpleDateFormat(" MMM dd H mm ss,yyyy");
		Tempstr = "";
		mydate.applyPattern("yyyy");
		i = Integer.parseInt(mydate.format(date));
		Tempstr = Tempstr + String.valueOf(i);
		return Tempstr;
	}

	/**
	 * 根据日期得到月份字符串
	 */
	public static final String getMonthFromDate(Date date) {
		String Tempstr;
		int i;
		SimpleDateFormat mydate = new SimpleDateFormat(" MM dd H mm ss,yyyy");
		Tempstr = "";
		mydate.applyPattern("MM");
		i = Integer.parseInt(mydate.format(date));
		Tempstr = Tempstr + String.valueOf(i);
		return Tempstr;
	}

	/**
	 * 根据日期得到日期字符串
	 */
	public static final String getDayFromDate(Date date) {
		String Tempstr;
		int i;
		SimpleDateFormat mydate = new SimpleDateFormat(" MM dd H mm ss,yyyy");
		Tempstr = "";
		mydate.applyPattern("dd");
		i = Integer.parseInt(mydate.format(date));
		Tempstr = Tempstr + String.valueOf(i);
		return Tempstr;
	}

	/**
	 * 根据日期得到小时字符串
	 */
	public static final String getHourFromDate(Date date) {
		String Tempstr;
		int i;
		SimpleDateFormat mydate = new SimpleDateFormat(" MM dd H mm ss,yyyy");
		Tempstr = "";
		mydate.applyPattern("H");
		i = Integer.parseInt(mydate.format(date));
		Tempstr = Tempstr + String.valueOf(i);
		return Tempstr;
	}

	/**
	 * 用于只输入年月日生成long型的时间格式
	 **/
	public static final long getTimeLong(int yy, int mm, int dd) {
		calendar.clear();
		calendar.set(yy, mm - 1, dd, 0, 0, 0);
		return calendar.getTimeInMillis();

	}

	/**
	 * 用于输入年月日小时分生成long型的时间格式
	 **/
	public static final long getTimeLong(int yy, int mm, int dd, int h, int m) {
		calendar.clear();
		calendar.set(yy, mm - 1, dd, h, m, 0);
		return calendar.getTimeInMillis();
	}

	/**
	 * 用于只输入年，月生成long型的时间格式
	 **/
	public static final long getTimeLong(int yy, int mm) {
		calendar.clear();
		calendar.set(yy, mm - 1, 0, 0, 0, 0);
		return calendar.getTimeInMillis();
	}

	/**
	 * 根据输入的初始日期和新增的月份到新增月份后的总时间
	 **/
	public static final long getTotalTime(Date startTime, long month) {
		calendar.setTime(startTime);
		calendar.add(Calendar.MONTH, (int) month);
		return calendar.getTimeInMillis();
	}

	/**
	 * 用于输入年月日小时分秒生成long型的时间格式
	 **/
	public static final long getTimeLong(int yy, int mm, int dd, int h, int m,
			int sec) {
		calendar.clear();
		calendar.set(yy, mm - 1, dd, h, m, sec);
		return calendar.getTimeInMillis();
	}

	/**
	 * 根据输入一个时间得到和现在的时间差
	 **/
	public static final String getLeaveTime(long tagTime) {
		long nowTime = System.currentTimeMillis();
		long leaveTime = 0;
		if (nowTime > tagTime)
			leaveTime = (nowTime - tagTime) / 1000;
		else
			leaveTime = (tagTime - nowTime) / 1000;
		long date = 0;
		long hour = 0;
		long minute = 0;
		// int second = 0;

		long dateTime = 0;
		long hourTime = 0;
		// long minuteTime = 0;

		String strDate = "";
		String strHour = "";
		String strMinute = "";
		// String strSecond = "";

		date = leaveTime / 86400;
		dateTime = date * 86400;
		hour = (leaveTime - dateTime) / 3600;
		hourTime = hour * 3600;
		minute = (leaveTime - dateTime - hourTime) / 60;
		// minuteTime = minute*60;

		// second = leaveTime - dateTime - hourTime-minuteTime;

		if (date > 0)
			strDate = date + "天";
		if (hour > 0 || (minute > 0 && date > 0))
			strHour = hour + "小时";
		if (minute > 0)
			strMinute = minute + "分";
		// if(second>0)
		// strSecond = second+"秒";

		return strDate + strHour + strMinute;
	}
	/**
	 * 字符串转为java.sql.date
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static java.sql.Date toSQLDate(String dateStr, String format)
	{
		try
		{
			if (dateStr == null || dateStr.equals(""))
				return null;
			return new java.sql.Date(formatDateStr(dateStr,format).getTime());
		}
		catch(Exception ex)
		{return null;}
		
	}
	public static void main(String[] args) throws Exception{
		Date d = new Date();
//		System.out.println(addTimeByMinutes(d,18).toString());
		 //System.out.println(addTimeByDay(d,18).toString());
//		System.out.println(formatDate(d).toString());
//		 System.out.println(getMonthBegin(formatDate(d).toString()));
//		 System.out.println(getMonthBegin("2008/07/19"));
//		 System.out.println(getMonthEnd("2008/07/19"));
//		System.out.println(addDate(d,15).toString());
	}
}
