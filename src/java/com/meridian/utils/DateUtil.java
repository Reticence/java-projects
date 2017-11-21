package com.meridian.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 常用时间工具类
 * </p>
 * 
 * @author Lennon.Wang
 * @site http://www.biubiu.net
 * @version 1.0
 * @date 2016-1-15
 * @since JDK 1.7
 * @copyright Copyright 2015 BeiJing MMEDNET. All rights reserved.
 */
public class DateUtil {

	public static String YMD_HMS = "yyyy-MM-dd HH:mm:ss";
	public static String YMDHMS = "yyyyMMddHHmmss";

	/**
	 * 得到指定月的天数
	 * */
	public static int getMonthLastDay(int year, int month) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 在小于10月和日前面补0
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @return 补齐后的日期字符串
	 */
	public static String getKongjian(String dateStr) {
		if (dateStr.indexOf("-") < 0)
			return dateStr;
		String[] date = dateStr.split("-");
		String year = date[0];
		String month = date[1];
		String day = date[2];
		if (Integer.parseInt(month) < 10) {
			month = "0" + month;
		}
		if (Integer.parseInt(day) < 10) {
			day = "0" + day;
		}
		return year + month + day;
	}

	/**
	 * 日期时间格式转换（20140510123210转成2014-05-10 12:32:10)
	 * 
	 * @param pattern
	 *            转换前日期时间格式
	 * @return 转换后日期时间格式
	 */
	public static String StringToString(String pattern) {
		if (pattern == null)
			return "";
		String year = pattern.substring(0, 4);
		String month = pattern.substring(4, 6);
		String date = pattern.substring(6, 8);
		String hour = pattern.substring(8, 10);
		String minute = pattern.substring(10, 12);
		String second = pattern.substring(12, 14);
		pattern = year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;

		return pattern;

	}

	/**
	 * 通过生日来获得年龄
	 * 
	 * @param birthDay
	 *            生日
	 * @return 年龄
	 */
	public static int calculateAgeByYear(Date birthDay) {
		if (null == birthDay) {
			return 0;
		}
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthDay)) {
			throw new IllegalArgumentException("出生时间大于当前时间!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		cal.setTime(birthDay);
		int yearBirth = cal.get(Calendar.YEAR);
		int age = yearNow - yearBirth;
		return age;
	}

	/**
	 * 根据出生元月日 计算出年龄
	 * 
	 * @param birthDay
	 *            生日
	 * @return 年龄
	 * @throws Exception
	 *             异常
	 */
	public static int calculateAge(Date birthDay) throws Exception {
		// 为了跟数据库对比统一，现在暂时只能比较年份
		return calculateAgeByYear(birthDay);
	}

	/**
	 * @Title: getPrintDate
	 * @Description: 取当前打印日期
	 * @param @return
	 * @return String
	 * @throws
	 */

	/**
	 * 获得当前日期（年月日）
	 * 
	 * @return 2014年5月10日
	 */
	public static String getPrintDate() {
		String printDate = "";
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		printDate += calendar.get(Calendar.YEAR) + "年";
		printDate += (calendar.get(Calendar.MONTH) + 1) + "月";
		printDate += calendar.get(Calendar.DATE) + "日";
		return printDate;
	}

	/**
	 * @Title: getTimestamp
	 * @Description: 将指定日期转换为 Timestamp
	 * @param @param dateStr
	 * @param @return
	 * @param @throws Exception
	 * @return Timestamp
	 * @throws
	 */

	/**
	 * 将日期字符（yyyyMMdd 00:00:00.000）格式转成Timestamp
	 * 
	 * @param dateStr
	 *            日期字符格式（yyyyMMdd 00:00:00.000）
	 * @return Timestamp
	 * @throws Exception
	 *             异常
	 */
	public static Timestamp getTimestamp(String dateStr) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd 00:00:00.000");
		return Timestamp.valueOf(sdf.format(getDate(dateStr, "yyyyMMdd")));
	}

	/**
	 * 从指定Timestamp中得到相应的日期(2003-09-08)
	 * 
	 * @param datetime
	 *            指定的Timestamp
	 * @return 日期 "2003-09-08"
	 */
	public static String getDateFromDateTime(Timestamp datetime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String str = "";
		try {
			str = sdf.format(datetime).toString();
		} catch (Exception e) {

		}

		return str;
	}

	/**
	 * 得到当前时间的时间戳
	 * 
	 * @return 当前时间的时间戳
	 */
	public static Timestamp getNowTimestamp() {
		long curTime = System.currentTimeMillis();
		return new Timestamp(curTime);
	}

	/**
	 * 判断当前格式的字符日期时间(yyyyMMddHHmmss)，是否当前时间戳的正负五分钟之内
	 * 
	 * @param timestamp
	 *            字符日期时间(yyyyMMddHHmmss)
	 * @param tsamp
	 *            时间戳(没用)
	 * @return true为是，false为不是
	 */
	public static boolean TimestampCompare(String timestamp, Timestamp tsamp) {

		Date ts;
		try {
			ts = new SimpleDateFormat("yyyyMMddHHmmss").parse(timestamp);
		} catch (ParseException e) {
			return false;
		}

		// 5分钟前
		Timestamp t1 = DateUtil.getNowTimestamp();
		t1.setMinutes(DateUtil.getNowTimestamp().getMinutes() - 30);

		// 5分钟后
		Timestamp t2 = DateUtil.getNowTimestamp();
		t2.setMinutes(DateUtil.getNowTimestamp().getMinutes() + 30);

		// 大于当前时间
		if (ts.compareTo(new Date(t1.getTime())) >= 0) {
			// 大于当前时间超过5分钟
			if (ts.compareTo(new Date(t2.getTime())) <= 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 把字符串转换为日期
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @param format
	 *            日期格式
	 * @return Date 日期
	 */
	public static Date strToDate(String dateStr, String format) {
		Date date = null;

		if (dateStr != null && (!dateStr.equals(""))) {
			DateFormat df = new SimpleDateFormat(format);
			try {
				date = df.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	/**
	 * 把字符串转换为日期，日期的格式为yyyyMMdd
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @return Date 日期
	 */
	public static Date strToDate(String dateStr) {
		Date date = null;

		if (dateStr != null && (!dateStr.equals(""))) {
			if (dateStr.matches("\\d{4}\\d{1,2}\\d{1,2}")) {

			} else {
				System.out.println(dateStr + " 格式不正确");
				return null;
			}
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			try {
				date = df.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	/**
	 * 把日期转换为字符串
	 * 
	 * @param date
	 *            日期实例
	 * @param format
	 *            日期格式
	 * @return 字符串
	 */
	public static String dateToStr(Date date, String format) {

		return (date == null) ? "" : new SimpleDateFormat(format).format(date);
	}

	/**
	 * 把日期转换为字符串（yyyy-MM-dd HH:mm:ss）
	 * 
	 * @param date
	 *            日期实例
	 * @return 字符串
	 */
	public static String dateToStr(Date date) {
		return (date == null) ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	/**
	 * @Title: TimeZoneDateToStr
	 * @Description:世界时间转换成本地时间/美国时间转换成中国时间
	 * @param @param date
	 * @param @param format
	 * @param @return
	 * @param @throws ParseException
	 * @return String
	 * @throws
	 */

	/**
	 * 世界时间转换成本地时间/美国时间转换成中国时间
	 * 
	 * @param date
	 *            日期
	 * @param format
	 *            格式化字符串
	 * @return 字符串
	 * @throws ParseException
	 *             异常
	 */
	public static String TimeZoneDateToStr(Date date, String format) throws ParseException {
		if (null == date) {
			return "";
		}
		TimeZone tz = TimeZone.getTimeZone("CHINA/Central");

		SimpleDateFormat sdf = new SimpleDateFormat(format);

		SimpleDateFormat test = new SimpleDateFormat(format);
		test.setTimeZone(tz);

		Date tempDate = test.parse(sdf.format(date));

		return sdf.format(tempDate);
	}

	/**
	 * 取得当前日期(yyyyMMdd)
	 * 
	 * @return 当前日期(yyyyMMdd)
	 */
	public static String getCurrentDate() {
		DateFormat f = new SimpleDateFormat("yyyyMMdd");
		return f.format(Calendar.getInstance().getTime());
	}

	/**
	 * 获得当前年月(yyyy-MM)
	 * 
	 * @return 当前年月(yyyy-MM)
	 */
	public static String getCurrentDate_MM() {
		DateFormat f = new SimpleDateFormat("yyyy-MM");
		return f.format(Calendar.getInstance().getTime());
	}

	/**
	 * 将指定的日期字符串转化为日期对象
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @return java.util.Date
	 */

	/**
	 * 将指定的日期字符串转化为日期对象 如果dateStr为空，刚取当前日期 如果format为空，则默认给(yyyyMMdd)
	 * 
	 * @param dateStr
	 *            日期字符
	 * @param format
	 *            格式字符串
	 * @return 日期
	 */
	public static Date getDate(String dateStr, String format) {
		if (dateStr == null) {
			return new Date();
		}
		if (format == null) {
			format = "yyyyMMdd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date date = sdf.parse(dateStr);
			return date;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获得当前时间字符串(yyyyMMddHHmmss)
	 * 
	 * @return 当前时间字符串(yyyyMMddHHmmss)
	 */
	public static String getCurrentTime() {
		Date date = new Date();
		String time = dateToStr(date, "yyyyMMddHHmmss");
		return time;
	}

	/**
	 * 获得当前时间的小时和分钟(HH:mm)
	 * 
	 * @return 当前时间的小时和分钟(HH:mm)
	 */
	public static String getCurrentTimehhmm() {
		Date date = new Date();
		String time = dateToStr(date, "HH:mm");
		return time;
	}

	/**
	 * 获得当前时间的年月日(yyyy-MM-dd)
	 * 
	 * @return 当前时间的年月日(yyyy-MM-dd)
	 */
	public static String getCurrentTimeyyyy_mm_dd() {
		Date date = new Date();
		String time = dateToStr(date, "yyyy-MM-dd");
		return time;
	}

	/**
	 * 字符串转日期，字符串格式为(yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param dateStr
	 *            字符串
	 * @return 日期
	 */
	public static Date StringToDate(String dateStr) {
		if (dateStr == null || dateStr.equals(""))
			return null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();

			return null;
		}
	}

	/**
	 * 查询出上一个月的第一天
	 * 
	 * @return 上一个月的第一天
	 */
	public static String GetLastMonthFirstDay() {
		// 取得系统当前时间
		Calendar cal = Calendar.getInstance();
		// 取得系统当前时间所在月第一天时间对象
		cal.set(Calendar.DAY_OF_MONTH, 1);
		// 日期减一,取得上月最后一天时间对象
		cal.add(Calendar.DAY_OF_MONTH, -1);
		cal.set(Calendar.DATE, 1);
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		return df.format(cal.getTime());

	}

	/**
	 * 查询出传入日期的上一个月的第一天
	 * 
	 * @param cal
	 *            日期
	 * @return 传入日期的上一个月的第一天
	 */
	public static String GetLastMonthFirstDay(Calendar cal) {
		// 取得系统当前时间所在月第一天时间对象
		cal.set(Calendar.DAY_OF_MONTH, 1);
		// 日期减一,取得上月最后一天时间对象
		cal.add(Calendar.DAY_OF_MONTH, -1);
		cal.set(Calendar.DATE, 1);
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		return df.format(cal.getTime());

	}

	/**
	 * 
	 * @Title: GetMonthFirstDay
	 * @Description: 查询出上一个月的第一天
	 * @param @param dformatStr
	 * @param @return
	 * @return String
	 * @throws
	 */

	/**
	 * 查询出传入字符串日期（yyyy-MM-dd）的上一个月的第一天
	 * 
	 * @param dformatStr
	 *            日期
	 * @return 传入日期的上一个月的第一天
	 */
	public static String GetLastMonthFirstDay(String dformatStr) {
		// 取得系统当前时间
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDate(dformatStr, "yyyy-MM-dd"));
		String data = GetLastMonthFirstDay(cal);
		return data;

	}

	/**
	 * 将yyyy-M-d 转换yyyy-MM-dd
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @return 转换后的日期字符串
	 */
	public static String DateChange(String dateStr) {
		if (dateStr == null || dateStr.equals(""))
			return null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.format(df.parse(dateStr));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 查询出当前月的最后一天
	 * 
	 * @return 当前月的最后一天日期(yyyy-MM-dd)
	 */
	public static String GetLastMonthLastDay() {
		Calendar cal = Calendar.getInstance();
		// 将下个月1号作为日期初始
		cal.set(Calendar.DATE, 1);
		// 下个月1号减去一天，即得到当前月最后一天
		cal.add(Calendar.DATE, -1);
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String day_end = df.format(cal.getTime());
		return day_end;
	}

	/**
	 * 查询出当前月的最后一天
	 * 
	 * @param format
	 *            格式化
	 * @return 当前月的最后一天日期
	 */
	public static String GetLastMonthLastDay1(String format) {
		Calendar cal = Calendar.getInstance();
		// 将下个月1号作为日期初始
		cal.set(Calendar.DATE, 1);
		// 下个月1号减去一天，即得到当前月最后一天
		cal.add(Calendar.DATE, -1);
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(format);
		String day_end = df.format(cal.getTime());
		return day_end;
	}

	/**
	 * 查询传入日期的月最后一天
	 * 
	 * @param dateStr
	 *            日期
	 * @return 传入日期的月最后一天
	 */
	public static String GetLastMonthLastDay(String dateStr) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(strToDate(dateStr));
		// 将下个月1号作为日期初始
		cal.set(Calendar.DATE, 1);
		// 下个月1号减去一天，即得到当前月最后一天
		cal.add(Calendar.DATE, -1);
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String day_end = df.format(cal.getTime());
		return day_end;
	}

	/**
	 * 查询出当前月的第一天(方法过时，建议用getNowMonthFirstDay())
	 * 
	 * @return 当前月的第一天
	 */
	@Deprecated
	public static String GetMonthFirstDay() {

		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DATE, 1);
		return df.format(c.getTime());

	}

	/**
	 * 查询出当前月的第一天(建议使用getNowMonthFirstDay(String format))
	 * 
	 * @param format
	 *            格式化字符串
	 * @return 出当前月的第一天
	 */
	@Deprecated
	public static String GetMonthFirstDay1(String format) {

		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(format);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DATE, 1);
		return df.format(c.getTime());

	}

	/**
	 * 获得当前月的第一天
	 * 
	 * @param dformatStr
	 *            格式化字符串
	 * @return 日期格式化后的字符串
	 */
	public static String GetMonthFirstDay(String dformatStr) {
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(dformatStr);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DATE, 1);
		return df.format(c.getTime());

	}

	/**
	 * @Title: GetMonthLastDay
	 * @Description: 查询出当前月的最后一天
	 * @param @return
	 * @return String
	 * @throws
	 */

	/**
	 * 查询出当前月的最后一天
	 * 
	 * @return 日期字符串(yyyy-MM-dd)
	 */
	public static String GetMonthLastDay() {
		Calendar cal = Calendar.getInstance();
		// 当前月＋1，即下个月
		cal.add(Calendar.MONTH, 1);
		// 将下个月1号作为日期初始zhii
		cal.set(Calendar.DATE, 1);
		// 下个月1号减去一天，即得到当前月最后一天
		cal.add(Calendar.DATE, -1);
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String day_end = df.format(cal.getTime());
		return day_end;
	}

	/**
	 * 查询出当前月的最后一天
	 * 
	 * @param dformatStr
	 *            格式化
	 * @return 格式化的当前月的最后一天
	 */
	public static String GetMonthLastDay(String dformatStr) {
		Calendar cal = Calendar.getInstance();
		// 当前月＋1，即下个月
		cal.add(Calendar.MONTH, 1);
		// 将下个月1号作为日期初始zhii
		cal.set(Calendar.DATE, 1);
		// 下个月1号减去一天，即得到当前月最后一天
		cal.add(Calendar.DATE, -1);
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(dformatStr);
		String day_end = df.format(cal.getTime());
		return day_end;
	}

	/**
	 * 查询出传入日期的当前月的最后一天
	 * 
	 * @param datestr
	 *            日期
	 * @param format
	 *            格式化
	 * @return 传入日期的当前月的最后一天的格式化字符串
	 */
	public static String GetThisMonthLastDay(String datestr, String format) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDate(datestr, format));
		// 当前月＋1，即下个月
		cal.add(Calendar.MONTH, 1);
		// 将下个月1号作为日期初始zhii
		cal.set(Calendar.DATE, 1);
		// 下个月1号减去一天，即得到当前月最后一天
		cal.add(Calendar.DATE, -1);
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(format);
		String day_end = df.format(cal.getTime());
		return day_end;
	}

	/**
	 * 查询出传入日期的当前月的最后一天(yyyy-MM-dd)
	 * 
	 * @param datestr
	 *            日期
	 * @return 传入日期的当前月的最后一天的格式化字符串
	 */
	public static String GetNextMonthFirstDay(String datestr) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDate(datestr, "yyyy-MM-dd"));
		// 当前月＋1，即下个月
		cal.add(cal.MONTH, 1);
		// 将下个月1号作为日期初始zhii
		cal.set(cal.DATE, 1);
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String day_end = df.format(cal.getTime());
		return day_end;
	}

	/**
	 * 字符串转字符串(yyyy-MM-dd转成yyyyMMddHHmmss和yyyyMMdd转成yyyyMMddHHmmss)
	 * 
	 * @param dateStr
	 *            字符串
	 * @param i
	 *            I
	 * @return 字符串
	 */
	public static String StringToString(String dateStr, Integer i) {
		String returnStr = "";
		if (i == 0) {
			if ("" != dateStr) {
				if (dateStr.indexOf("-") > 0) {
					Date date = StringToDate(dateStr + " 00:00:00");
					DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
					returnStr = df.format(date);
				} else {
					returnStr = dateStr + "000000";
				}
			} else {
				returnStr = "0";
			}

		}
		if (i == 1) {
			if ("" != dateStr) {
				if (dateStr.indexOf("-") > 0) {
					Date date = StringToDate(dateStr + " 23:59:59");
					DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
					returnStr = df.format(date);
				} else {
					returnStr = dateStr + "235959";
				}
			} else {
				Date date = new Date();
				DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				returnStr = df.format(date);
			}
		}

		return returnStr;

	}

	/**
	 * 获得时间的星期
	 * 
	 * @param sdate
	 *            时间
	 * @return 星期
	 */
	public static String getWeek(String sdate) {
		// 再转换为时间
		Date date = strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	/**
	 * 判断是否时间格式:yyyymmdd
	 * 
	 * @param datestr
	 *            时间字符串
	 * @return true为是，flase为否
	 */
	public static boolean isDateFormat(String datestr) {
		String reg = "";
		if (datestr.length() == 14) {
			reg = "\\d{14}";
		} else if (datestr.length() == 8) {
			reg = "\\d{8}";
		} else {
			return false;
		}
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(datestr);
		boolean bl = m.matches();
		return bl;
	}

	// public static Date strToDate(String strDate) {
	// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	// ParsePosition pos = new ParsePosition(0);
	// Date strtodate = formatter.parse(strDate, pos);
	// return strtodate;
	// }

	/**
	 * 日期是否得到于当前时间
	 * 
	 * @param timestamp
	 *            时间戳
	 * @return true为是，false为不是
	 */
	public static boolean DatepCompare(String timestamp) {

		Date ts;
		try {
			ts = new SimpleDateFormat("yyyy-M-d hh:mm:ss").parse(timestamp);
		} catch (ParseException e) {

			return false;
		}

		Timestamp t1 = DateUtil.getNowTimestamp();
		// 大于当前时间
		if (ts.compareTo(new Date(t1.getTime())) > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 得到当前的系统时间(yyyy-M-d)
	 * 
	 * @return 当前的系统时间(yyyy-M-d)
	 */
	public static String getNowTime() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
		return sdf.format(d);
	}

	/**
	 * 获得当前格式的时间（如果不传Format，则返回yyyyMMdd）
	 * 
	 * @param format
	 *            时间格式化
	 * @return 字符串
	 */
	public static String getCurrentDate(String format) {

		if (format == null) {
			format = "yyyyMMdd";
		}
		DateFormat f = new SimpleDateFormat(format);
		return f.format(Calendar.getInstance().getTime());
	}

	/**
	 * 通过年月日数字，获得时间格式的字符串（如果不传Format，则返回yyyyMMdd）
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @param format
	 *            格式化
	 * @return 字符串
	 */
	public static String getYourDate(int year, int month, int day, String format) {
		if (format == null) {
			format = "yyyyMMdd";
		}
		DateFormat f = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DATE, day);
		return f.format(c.getTime());

	}

	/**
	 * 比较传入时间是否大于当前时间
	 * 
	 * @param timestamp
	 *            时间格式（yyyyMMddhhmmss）
	 * @return true为大，false为小
	 */
	public static boolean compareToToday(String timestamp) {

		Date ts;
		try {
			ts = new SimpleDateFormat("yyyyMMddhhmmss").parse(timestamp);
		} catch (ParseException e) {

			return false;
		}

		Timestamp t1 = DateUtil.getNowTimestamp();
		// 大于当前时间
		if (ts.compareTo(new Date(t1.getTime())) > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 通过日期字符串和字符格式转换成Timestamp
	 * 
	 * @param timestamp
	 *            日期格式
	 * @param format
	 *            字符格式转换
	 * @return Timestamp
	 */
	public static Timestamp changerDateFormat(String timestamp, String format) {
		Date ts = null;
		Timestamp tt = null;
		try {
			ts = new SimpleDateFormat(format).parse(timestamp);
			tt = new Timestamp(ts.getTime());
		} catch (ParseException e) {

		}
		return tt;
	}

	/**
	 * 通过Timestamp生成对格式转换的字符串
	 * 
	 * @param timestamp
	 *            时间戳
	 * @param format
	 *            格工转换
	 * @return 转换后的字符串
	 */
	public static String changerDateFormat(Timestamp timestamp, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(timestamp);
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * N个月的 第一天
	 * 
	 * @param n
	 *            N个月
	 * @return 字符串
	 */
	public static String beforeMonths_NFirst(int n) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(date);
		String[] item = time.split("-");
		int year = Integer.parseInt(item[0]);
		int month = Integer.parseInt(item[1]);
		int day = 1;
		if ((month - n) <= 0) {
			month = month + 12 - n;
			year = year - 1;
		} else {
			month = month - n;
		}
		String months = "";
		if (month < 10) {
			months = "0" + month;
		} else {
			months = String.valueOf(month);
		}
		time = year + "-" + months + "-" + "0" + day;
		return time;
	}

	/**
	 * N个月的 第一天
	 * 
	 * @param datestr
	 *            日期字符串
	 * @param n
	 *            N个月
	 * @return 字符串
	 */
	public static String beforeThisMonths_NFirst(String datestr, int n) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(datestr);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		String time = sdf.format(date);
		String[] item = time.split("-");
		int year = Integer.parseInt(item[0]);
		int month = Integer.parseInt(item[1]);
		int day = 1;
		if ((month - n) <= 0) {
			month = month + 12 - n;
			year = year - 1;
		} else {
			month = month - n;
		}
		String months = "";
		if (month < 10) {
			months = "0" + month;
		} else {
			months = String.valueOf(month);
		}
		time = year + "-" + months + "-" + "0" + day;
		return time;
	}

	/**
	 * N个月的 第一天
	 * 
	 * @param n
	 *            n个月
	 * @param format
	 *            格式化
	 * @return 字符串
	 */
	public static String beforeMonths_NFirst(int n, String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String time = sdf.format(date);
		String[] item = time.split("-");
		int year = Integer.parseInt(item[0]);
		int month = Integer.parseInt(item[1]);
		if ((month - n) <= 0) {
			month = month + 12 - n;
			year = year - 1;
		} else {
			month = month - n;
		}
		String months = "";
		if (month < 10) {
			months = "0" + month;
		} else {
			months =String.valueOf(month);
		}
		time = year + "-" + months;
		return time;
	}

	/**
	 * 根据你传过来的 日期正规格式 返回 yyyy-MM-dd的格式
	 * 
	 * @param dstr
	 *            日期字符串
	 * @param sourceFormat
	 *            源格式
	 * @param format
	 *            目标格式
	 * @return 字符串
	 */
	public static String changerDateFormat(String dstr, String sourceFormat, String format) {
		SimpleDateFormat tempsdf = new SimpleDateFormat(sourceFormat);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String datestr = null;
		try {
			Date date = tempsdf.parse(dstr);
			datestr = sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datestr;
	}

	/**
	 * 将yyyyMMddhhmmss 转换为yyyy-MM-dd hh:mm:ss
	 * 
	 * @param dateStr
	 *            源日期字符串
	 * @return 转换后日期的字符串
	 */
	public static String DateChange1(String dateStr) {

		if (dateStr == null || dateStr.equals(""))
			return null;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.format(df.parse(dateStr));
		} catch (Exception e) {
			return null;
		}
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		// try {
		// String year = dateStr.substring(0, 4);
		// String month = dateStr.substring(4, 6);
		// String day = dateStr.substring(6, 8);
		// String hour = dateStr.substring(8, 10);
		// String minuts = dateStr.substring(10, 12);
		// String second = dateStr.substring(12);
		// dateStr = year + "-" + month + "-" + day + " " + hour + ":"
		// + minuts + ":" + second;
		// return sdf.format(df.parse(dateStr));
		// } catch (ParseException e) {
		// e.printStackTrace();
		// return null;
		// }

	}

	/**
	 * is LleapYear
	 * 
	 * @param year
	 * @return
	 */

	/**
	 * 年份是否为闰年
	 * 
	 * @param year
	 *            年
	 * @return true为是，flase为不是
	 */
	public static boolean isLleapYear(Integer year) {
		if (((year % 100 == 0) && (year % 400 == 0)) || ((year % 100 != 0) && (year % 4 == 0))) {
			return true;
		}
		return false;
	}

	/**
	 * 获得某年某月的天数
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 天数
	 */
	public static Integer getMonthNum(Integer year, Integer month) {
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31;
		case 2:
			if (isLleapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		default:
			return 30;
		}
	}

	/**
	 * 获得年月日从开始的时间或者结束时间
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @param type
	 *            0为开始时间，1为结束时间
	 * @return 字符串
	 */
	public static String getCurrentDate(Integer year, Integer month, Integer day, Integer type) {
		String m = "0";
		String d = "0";
		String date = year.toString();
		if (month < 10) {
			m = m + month;
			date = date + m;
		} else {
			date = date + month;
		}
		if (day < 10) {
			d = d + day;
			date = date + d;
		} else {
			date = date + day;
		}
		if (type == 0) {
			return date = date + "000000";
		}
		return date = date + "235959";

	}

	/**
	 * 获得结束日期和开始日期的天数
	 * 
	 * @param begin
	 *            开始日期
	 * @param end
	 *            结束时期
	 * @return 天数
	 */
	public static int getDays(Date begin, Date end) {
		int days = 0;
		long tp = end.getTime() - begin.getTime();
		days = (int) (tp / (1000 * 60 * 60 * 24));
		return days;
	}

	/**
	 * 日期转换20140510转成2014-05-10
	 * 
	 * @param dateStr
	 *            源日期字符串
	 * @return 转换后的字符串
	 */
	public static String stringDatefomat(String dateStr) {
		if (dateStr != null && !dateStr.equals("") && dateStr.length() >= 8) {
			dateStr = dateStr.substring(0, 8);
			String year = dateStr.substring(0, 4);
			String month = dateStr.substring(4, 6);
			String date = dateStr.substring(6, 8);
			dateStr = year + "-" + month + "-" + date;

		}
		return dateStr;
	}

	/**
	 * 计算多少年后的日期
	 * 
	 * @param dyear
	 *            年
	 * @param dateStr
	 *            日期时间
	 * @param dateFormat
	 *            格式化
	 * @return 字符串
	 */
	public static String distanceYear(int dyear, String dateStr, String dateFormat) {
		String returnDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			// 当前月＋dyear，即下dyear个月
			cal.add(Calendar.YEAR, dyear);
			returnDate = sdf.format(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return returnDate;
	}

	/**
	 * 判断当前时间是否为晚上(下午六点以后到第二天早上六点算晚上)
	 * 
	 * @return true为晚上，false为白天
	 */
	public static boolean isAtNight() {
		Calendar today = Calendar.getInstance(TimeZone.getDefault());
		int hour = today.get(Calendar.HOUR_OF_DAY);
		if (hour >= 18 || hour < 6) {
			return true;
		}
		return false;

	}

	/**
	 * 通过Long型时间戳来获得字符串(yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param times
	 *            Long时间戳
	 * @return 日期字符串(yyyy-MM-dd HH:mm:ss)
	 */
	public static String getDateStr(Long times) {
		String timeStr = "";
		if (!CheckNull.isNull(times)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date(times);
			timeStr = sdf.format(date);
		}
		return timeStr;
	}

	/**
	 * 通过long来获得字符串日期
	 * 
	 * @param l
	 *            Long时间戳
	 * @param format
	 *            格式
	 * @return 字符串日期
	 */
	public static String getYMDByLong(Long l, String format) {
		if (CheckNull.isNull(l)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(l));
	}

	/**
	 * 通过Timestamp格式来获得Long时间戳
	 * 
	 * @param obj
	 *            时间戳
	 * @return Long时间戳
	 */
	public static Long getLongByTimestamp(Object obj) {
		if (CheckNull.isNull(obj)) {
			return null;
		}
		try {
			Timestamp timestamp = (Timestamp) obj;
			return timestamp.getTime();
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 通过相差返回23小时0分钟5秒
	 * 
	 * @param difference
	 *            long时间戳
	 * @return 文字
	 */
	public static String getDifferenceStr(long difference) {
		int day = (int) (difference / (1000 * 60 * 60 * 24));
		difference = difference - (1000 * 60 * 60 * 24 * day);
		int hour = (int) (difference / (1000 * 60 * 60));
		difference = difference - (1000 * 60 * 60 * hour);
		int minute = (int) (difference / (1000 * 60));
		difference = difference - (1000 * 60 * minute);
		int second = (int) (difference / 1000);
		StringBuffer sb = new StringBuffer();
		if (day > 0) {
			sb.append(day).append("天").append(hour).append("小时").append(minute).append("分钟").append(second).append("秒");
		} else {
			if (hour > 0) {
				sb.append(hour).append("小时").append(minute).append("分钟").append(second).append("秒");
			} else {
				if (minute > 0) {
					sb.append(minute).append("分钟").append(second).append("秒");
				} else {
					sb.append(second).append("秒");
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 通过两个数相差返回23小时0分钟5秒
	 * 
	 * @param start
	 *            开始时间的long
	 * @param end
	 *            结束时间的long
	 * @return 文字
	 */
	public static String getDifferenceStr(long start, long end) {
		if (end < start) {
			return "";
		}

		long difference = end - start;
		return getDifferenceStr(difference);
	}

	/**
	 * 通过两个日期相差返回23小时0分钟5秒
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 文字
	 */
	public static String getDifferenceStr(Date startDate, Date endDate) {
		if (CheckNull.isNull(startDate) || CheckNull.isNull(endDate)) {
			return "";
		}
		return getDifferenceStr(startDate.getTime(), endDate.getTime());
	}

	/**
	 * 给传入时间加几个月得到新的时间
	 * 
	 * @param month
	 *            加几个月
	 * @param date
	 *            时间
	 * @return 新的时间
	 */
	public static Date distanceMonths(int month, Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, month);
		Date dateD = cal.getTime();
		return dateD;
	}

	/**
	 * 计算多少月后的日期
	 * 
	 * @param dmonth
	 *            加的月数
	 * @param dateStr
	 *            时间字符串
	 * @param dateFormat
	 *            格式
	 * @return 多少月的日期字符串
	 */
	public static String distanceMonths(int dmonth, String dateStr, String dateFormat) {
		String returnDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			// 当前月＋dyear，即下dyear个月
			cal.add(Calendar.MONTH, dmonth);
			returnDate = sdf.format(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return returnDate;
	}

	/**
	 * 获得加入多少天后的日期
	 * 
	 * @param days
	 *            天数
	 * @param date
	 *            日期
	 * @return 加入多少天后的日期
	 */
	public static Date distanceDays(int days, Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		Date dateD = cal.getTime();
		return dateD;
	}

	/**
	 * 获得加入多少天后的日期字符串
	 * 
	 * @param dday
	 *            天数
	 * @param dateStr
	 *            日期字符串
	 * @param dateFormat
	 *            格式
	 * @return 加入多少天后的日期
	 */
	public static String distanceDays(int dday, String dateStr, String dateFormat) {
		String returnDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			// 当前天＋dday，即下个dday天
			cal.add(Calendar.DATE, dday);
			returnDate = sdf.format(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return returnDate;
	}

	/**
	 * 判断是否同年同月
	 * 
	 * @param month
	 *            月
	 * @param createtime
	 *            时间
	 * @return true为是，false为否
	 */
	public static boolean isWithYearMonth(String month, String createtime) {
		boolean bl = false;
		String start = month + "000000";
		try {
			Date startdate = StringToDateTimeSmap(start);
			Date createtimedate = StringToDateTimeSmap(createtime);

			if ((startdate.getYear() == createtimedate.getYear())
					&& (startdate.getMonth() == createtimedate.getMonth())) {
				bl = true;
			}
		} catch (Exception e) {
		}
		return bl;
	}

	/**
	 * String(yyyyMMddHHmmss)转成Date
	 * 
	 * @param dateStr
	 *            字符串
	 * @return 日期
	 */
	public static Date StringToDateTimeSmap(String dateStr) {
		if (dateStr == null || dateStr.equals(""))
			return null;
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			return df.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获得某月的天数
	 * 
	 * @param datestr
	 *            字符串
	 * @param format
	 *            格式
	 * @return 天数
	 */
	public static int getMonthDays(String datestr, String format) {

		Calendar rightNow = Calendar.getInstance();

		int days = 0;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			rightNow.setTime(sdf.parse(datestr));
			days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
		} catch (Exception e) {
		}
		return days;
	}

	/**
	 * 得到某天的星期
	 * 
	 * @param datestr
	 *            日期字符串
	 * @param format
	 *            格式
	 * @return 星期
	 */
	public static int getDayWeek(String datestr, String format) {

		Calendar rightNow = Calendar.getInstance();

		int week = 0;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			rightNow.setTime(sdf.parse(datestr));
			week = rightNow.get(Calendar.DAY_OF_WEEK);
		} catch (Exception e) {
		}
		return week;
	}

	/**
	 * 得到某月第N周的开始日期
	 * 
	 * @param datestr
	 *            日期
	 * @param format
	 *            格式
	 * @param n
	 *            N周
	 * @return 字符
	 */
	public static String getStartDateOfWeek(String datestr, String format, Integer n) {
		Calendar cal = Calendar.getInstance();

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			cal.setTime(sdf.parse(datestr));
			cal.set(Calendar.DAY_OF_WEEK, 1);
			cal.set(Calendar.WEEK_OF_MONTH, n);
			Integer month = cal.get(Calendar.MARCH) + 1;

			datestr = getKongjian(cal.get(Calendar.YEAR) + "-" + month + "-" + cal.get(Calendar.DATE));
		} catch (Exception e) {
		}
		return datestr;
	}

	/**
	 * @Title: getMonthWeekOfN
	 * @Description: 获得某天是当前月的第几周
	 * @param datestr
	 * @param format
	 * @param day
	 * @return
	 * 
	 */

	/**
	 * 获得某天是当前月的第几周
	 * 
	 * @param datestr
	 *            字符串
	 * @param format
	 *            格式
	 * @param day
	 *            天
	 * @return 第几击
	 */
	public static int getMonthWeekOfN(String datestr, String format, int day) {
		Calendar cal = Calendar.getInstance();

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			cal.setTime(sdf.parse(datestr));
			cal.set(Calendar.DATE, day);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return cal.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 获取N天后的日期
	 * 
	 * @param datestr
	 *            日期字符串
	 * @param format
	 *            日期格式
	 * @param nDay
	 *            N天后
	 * @return 字符串
	 * 
	 */
	public static String getAfterOfNDay(String datestr, String format, int nDay) {
		Calendar cal = Calendar.getInstance();

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			cal.setTime(sdf.parse(datestr));
			cal.add(Calendar.DATE, nDay);
			Integer month = cal.get(Calendar.MARCH) + 1;

			datestr = getKongjian(cal.get(Calendar.YEAR) + "-" + month + "-" + cal.get(Calendar.DATE));

		} catch (Exception e) {
		}
		return datestr;
	}

	/**
	 * 取时间与当前时间对比的相差天数
	 * 
	 * @param date
	 *            时间
	 * @return 相差天数
	 */
	public static int getDayByNowTimeCompare(Date date) {
		if (CheckNull.isNull(date)) {
			return 0;
		}
		Calendar dateCa = Calendar.getInstance();
		dateCa.setTime(date);
		dateCa.set(Calendar.HOUR_OF_DAY, 0);
		dateCa.set(Calendar.MINUTE, 0);
		dateCa.set(Calendar.SECOND, 0);
		Calendar nowDate = Calendar.getInstance();
		nowDate.set(Calendar.HOUR_OF_DAY, 0);
		nowDate.set(Calendar.MINUTE, 0);
		nowDate.set(Calendar.SECOND, 0);
		long temp = nowDate.getTimeInMillis() - dateCa.getTimeInMillis();
		int day = ((Long) (temp / (1000 * 3600 * 24))).intValue();
		return day;

	}

	/**
	 * @Title: nowTimeCompareTime
	 * @Description: 取当前时间与传入时间的比较
	 * @param date
	 * @return
	 * 
	 */

	/**
	 * 取当前时间与传入时间的比较
	 * 
	 * @param date
	 *            时间
	 * @return 字符
	 */
	public static String nowTimeCompareTime(Date date) {
		if (CheckNull.isNull(date)) {
			return null;
		}

		Date now = new Date();
		long temp = now.getTime() - date.getTime();
		int hour = (int) (temp / (60 * 60 * 1000));

		if (hour >= 24) {
			return dateToStr(date, "yyyy-MM-dd");
		}
		if (hour < 1) {
			int minute = (int) (temp / (60 * 1000));

			if (minute < 1) {
				return "刚刚";
			}
			return minute + "分钟前";
		}
		return hour + "小时前";
	}

	public static String timeCompareTime(Date date) {
		if (CheckNull.isNull(date)) {
			return null;
		}
		Date now = new Date();

		int dayDifference = dayDifference(date, now);

		String resultStr = dateToStr(date, "yyyy/MM/dd");

		if (dayDifference == 0) {
			resultStr = dateToStr(date, "HH:mm");
		} else {

			int mont = Integer.parseInt(getCurrentWeek("yyyyMMdd", 1));
			int ser = Integer.parseInt(getCurrentWeek("yyyyMMdd", 7));
			int datastr = Integer.parseInt(dateToStr(date, "yyyyMMdd"));
			if (datastr >= mont && datastr <= ser) {
				resultStr = getWeek(dateToStr(date, "yyyyMMdd"));
			}
		}

		return resultStr;
	}

	/**
	 * 从当天开始获取一个月的指定星期的日期数组
	 * 
	 * @param weeks
	 *            数据
	 * @return 指定星期的日期数组
	 */
	public static List<String> getOneMonthWeekDate(String[] weeks) {
		List<String> dateList = new ArrayList<String>();

		String currDate = getCurrentDate();

		String nextMonthToday = distanceMonths(1, currDate, "yyyyMMdd");

		String tempDate = "";

		if (!CheckNull.isNull(weeks)) {
			for (int i = 0; i <= 31; i++) {
				tempDate = getAfterOfNDay(currDate, "yyyyMMdd", i);
				Date temp = getDate(tempDate, "yyyyMMdd");

				int tempWeek = temp.getDay();

				for (String week : weeks) {
					if (week.equals(tempWeek + "")
							&& TranUtil.getIntByObject(nextMonthToday) >= TranUtil.getIntByObject(tempDate)) {
						dateList.add(tempDate);
					}
				}
			}

		}

		return dateList;
	}

	/**
	 * 获得当月的第一天
	 * 
	 * @return 当月的第一天
	 */
	public static String getNowMonthFirstDay() {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DATE, 1);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		return dateToStr(now.getTime());
	}

	/**
	 * 获得当月的第一天的固定格式
	 * 
	 * @param format
	 *            格式化字符
	 * @return 当月的第一天
	 * 
	 */
	public static String getNowMonthFirstDay(String format) {
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DATE, 1);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		return dateToStr(now.getTime(), format);

	}

	/**
	 * 获得本周星期几的日期
	 * 
	 * @param format
	 *            格式
	 * @param week
	 *            周几
	 * @return 字符
	 */
	public static String getCurrentWeek(String format, int week) {
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + week);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(c.getTime());

	}

	/**
	 * 字符串转时间LONG秒
	 * 
	 * @param dateStr
	 *            字符串
	 * @return Long秒
	 */
	public static Long dateStringToLong(String dateStr) {
		Date stringToDate = StringToDate(dateStr);
		if (CheckNull.isNull(stringToDate)) {
			return null;
		}
		return stringToDate.getTime();
	}

	/**
	 * 字符串转时间LONG秒（按照后面的格式）
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @param format
	 *            格式
	 * @return Long秒
	 * 
	 */
	public static Long dateStringToLong(String dateStr, String format) {
		Date stringToDate = strToDate(dateStr, format);
		if (CheckNull.isNull(stringToDate)) {
			return null;
		}
		return stringToDate.getTime();
	}

	/**
	 * 时间延长，单位分钟
	 * 
	 * @param time
	 *            时间字符串
	 * @param minutes
	 *            分钟数
	 * @return 字符串
	 * 
	 */
	public static String dateDelayByMinutes(String time, int minutes) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd" + time + "ss");
		String ret = sdf.format(new Date());
		sdf = new SimpleDateFormat("yyyyMMddHH:mmss");
		try {
			Date parse = sdf.parse(ret);
			long t = parse.getTime() + 1000 * 60 * minutes;
			parse = new Date(t);
			sdf = new SimpleDateFormat("HHmmss");
			ret = sdf.format(parse);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 两个时间的差距天数
	 * 
	 * @param startDay
	 *            开始时间
	 * @param endDay
	 *            结果时间
	 * @return 天数
	 * 
	 */
	public static int dayDifference(Date startDay, Date endDay) {
		if (CheckNull.isNull(startDay)) {
			return 0;
		}
		if (CheckNull.isNull(endDay)) {
			endDay = new Date();
		}
		long start = startDay.getTime();
		long end = endDay.getTime();
		Long temp = (end - start) / (1000 * 3600 * 24);
		return temp.intValue();
	}

	/**
	 * 通过Long型获得日期
	 * 
	 * @param times
	 *            时间
	 * @param objformat
	 *            格式
	 * @return 日期
	 */
	public static Date getStringByLongTimes(Long times, String objformat) {
		Date date = getDate(getCurrentTime(), "yyyyMMddHHmmss");
		try {
			java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String day_end = df.format(times);
			date = df.parse(day_end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 所有周周一的日期
	 * 
	 * @param time
	 *            时间
	 * @return 周一的日期
	 */
	public static String convertWeekByDate(Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天

		if (1 == dayWeek) {

			cal.add(Calendar.DAY_OF_MONTH, -1);

		}

		System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期

		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一

		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天

		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值

		String imptimeBegin = sdf.format(cal.getTime());

		System.out.println("所在周星期一的日期：" + imptimeBegin);
		return imptimeBegin;

	}

	/**
	 * 获得传入Date天的第一秒，如果为NULL当前天的第一秒
	 * 
	 * @param today
	 *            日期
	 * @return 第一秒
	 */
	public static String getDateByTodayFirstSeconds(Date today) {
		Calendar c = Calendar.getInstance();
		if (!CheckNull.isNull(today)) {
			c.setTime(today);
		}

		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return dateToStr(c.getTime());
	}

	/**
	 * 获得传入Date天的最后一秒，如果为NULL当前天的最后一秒
	 * 
	 * @param today
	 *            日期
	 * @return 最后一秒
	 */
	public static String getDateByTodayLastSeconds(Date today) {
		Calendar c = Calendar.getInstance();
		if (!CheckNull.isNull(today)) {
			c.setTime(today);
		}
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return dateToStr(c.getTime());
	}

	/**
	 * 获得日期对象的时间
	 * 
	 * @param date
	 *            日期
	 * @param hhmmss
	 *            字符串
	 * @return 日期
	 */
	public static Date getDateHHmmss(Date date, String hhmmss) {
		Date d = new Date(date.getTime());
		String[] times = hhmmss.split("[:]");
		d.setHours(TranUtil.getIntByString(times[0]));
		d.setMinutes(TranUtil.getIntByString(times[1]));
		d.setSeconds(TranUtil.getIntByString(times[2]));
		return d;
	}

	public static void main(String[] args) {
		System.out.println(getWeek("20150714"));
		System.out.println(getCurrentWeek("yyyy-MM-dd", 7));
	}
	
	/**
	 * 转换时间将 mm/dd/yy 或 yyyy-mm-dd转换正常时间 2016-01-01
	 * 
	 * @param dateType
	 *            时间类型
	 * @return 转换后的时间 2016-01-01
	 * @throws ParseException 
	 */
	public static String getDates(String dateType) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		dateType = dateType.trim();
		int space = dateType.indexOf(" ");
		dateType = space > 0 ? dateType.substring(0, space) : dateType;
		dateType = dateType.replace(".", "/");
		if(dateType.indexOf("/")>0){
			Date testDate = new Date(dateType);
	    	return format.format(testDate);
		} else if (InstanceofNumber.isInteger(dateType)) {
		    Date currdate = format.parse("1900-01-01");
	        Calendar ca = Calendar.getInstance();
	        ca.setTime(currdate);
	        ca.add(Calendar.DATE, Integer.parseInt(dateType) - 2);
	        currdate = ca.getTime();
		    return format.format(currdate);
		} else {
			return format.format(format.parse(dateType));
		}
	}
	
	/**
	 * 根据日期获得年龄
	 * 
	 * @author ck.zhang
	 * @date 2017年3月8日 上午11:28:11
	 * @param birthDate
	 * @return
	 */
	public static int getAge(Date birthDate) {
		if (birthDate == null)
			return 0;
		int age = 0;
		Date now = new Date();
		SimpleDateFormat format_y = new SimpleDateFormat("yyyy");
		SimpleDateFormat format_M = new SimpleDateFormat("MM");
		String birth_year = format_y.format(birthDate);
		String this_year = format_y.format(now);
		String birth_month = format_M.format(birthDate);
		String this_month = format_M.format(now);
		// 初步，估算
		age = Integer.parseInt(this_year) - Integer.parseInt(birth_year);
		// 如果未到出生月份，则age - 1
		if (this_month.compareTo(birth_month) < 0)
			age -= 1;
		if (age < 0)
			age = 0;
		return age;
	}
	
	

}
