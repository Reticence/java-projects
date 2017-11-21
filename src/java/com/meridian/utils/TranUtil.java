package com.meridian.utils;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * 转换类
 * 
 * @author victor 2013-7-4
 * @since 2.0
 */
public class TranUtil {

	/**
	 * 通过Timestamp获得Long型
	 * 
	 * @param obj
	 *            Timestamp数据
	 * @return Long数据
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
	 * strArr
	 */
	private static String strArr[] = { "_", "%", "+", "%25" };

	/**
	 * objStr
	 */
	private static String objStr = "$$$$";

	/**
	 * Obj转换成Integer
	 * 
	 * @param object
	 *            object
	 * @return Integer类型
	 */
	public static Integer getIntegerByObject(Object object) {
		if (object == null) {
			return null;
		}

		try {
			return Integer.parseInt(object.toString().trim());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Obj转换成Long类型
	 * 
	 * @param object
	 *            Obj类型
	 * @return Long类型
	 */
	public static Long getLongByObject(Object object) {
		if (object == null) {
			return null;
		}

		try {
			return Long.parseLong(object.toString().trim());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Obj转成double类型
	 * 
	 * @param object
	 *            Obj数据
	 * @return double类型
	 */
	public static Double getDoubleByObject(Object object) {
		if (object == null) {
			return null;
		}

		try {
			return Double.parseDouble(object.toString().trim());
		} catch (Exception e) {
			return null;
		}
	}

	public static double getDoubByObject(Object object) {
		if (object == null) {
			return 0;
		}

		try {
			return Double.parseDouble(object.toString().trim());
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Obj转换成int类型
	 * 
	 * @param object
	 *            Obj类型
	 * @return int类型
	 */
	public static int getIntByObject(Object object) {
		if (object == null) {
			return 0;
		}

		try {
			return Integer.parseInt(object.toString().trim());
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 通过Object获得String类型
	 * 
	 * @param object
	 *            Obj类型
	 * @return String类型
	 */
	public static String getStringByObject(Object object) {
		return object == null ? "" : object.toString();
	}

	/**
	 * 通过字符串获得了符串，如果为空，则返回空字符串
	 * 
	 * @param str
	 *            字符串
	 * @return 字符串
	 * 
	 */
	public static String getStringByString(String str) {
		return CheckNull.isNull(str) ? "" : str.trim();
	}

	/**
	 * 字符串转换Integer型。
	 * 
	 * @param str
	 *            字符串
	 * @return Integer数据
	 */
	public static Integer getIntegerByString(String str) {
		Integer ret = new Integer(0);
		try {
			if (!CheckNull.isNull(str)) {
				ret = Integer.valueOf(str);
			}
		} catch (NumberFormatException e) {
		}
		return ret;
	}

	/**
	 * @Title: getTimestampByObject
	 * @Description: Object转换Timestamp
	 * @param obj
	 * @return
	 * 
	 */

	/**
	 * Object是不是Timestamp类型，如果是，则直接强转
	 * 
	 * @param obj
	 *            数据
	 * @return Timestamp类型
	 */
	public static Timestamp getTimestampByObject(Object obj) {
		if (obj instanceof Timestamp) {
			return (Timestamp) obj;
		}
		return null;
	}

	/**
	 * Object转换Date
	 * 
	 * @param obj
	 *            object类型
	 * @return Date
	 */
	public static Date getDateByObject(Object obj) {
		if (obj instanceof Date) {
			return (Date) obj;
		}
		return null;
	}

	/**
	 * 字符串，前面填充0.如果字段长度超过传入的长度，则原字符串返回
	 * 
	 * @param str
	 *            字符串
	 * @param length
	 *            长度
	 * @return 字符串
	 */
	public static String frontAutomaticFilling(String str, int length) {
		if (CheckNull.isNull(str)) {
			return "";
		}

		if (str.length() > length) {
			return str;
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 1; i < length; i++) {
			sb.append("0");
		}
		sb.append(str);
		return sb.substring(sb.length() - length, sb.length());
	}

	/**
	 * String转换成Long
	 * 
	 * @param string
	 *            字符串
	 * @return Long类型
	 */
	public static Long getLongByString(String string) {
		if (string == null) {
			return 0l;
		}

		try {
			return Long.parseLong(string.trim());
		} catch (Exception e) {
			return 0l;
		}
	}

	/**
	 * 组装成In所需要的数据
	 * 
	 * @param objs
	 *            数组集合
	 * @return 字符串
	 */
	public static String getSqlStringByList(List objs) {
		if (CheckNull.isNull(objs)) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		if (objs.size() == 1) {
			return "'" + objs.get(0).toString() + "'";
		}
		for (int i = 0; i < objs.size(); i++) {
			sb.append("'").append(objs.get(i)).append("'");
			if (objs.size() - 1 != i) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	/**
	 * 字符创过滤，超过length长度就用省略号代替
	 * 
	 * @param str
	 *            字符串
	 * @param length
	 *            长度
	 * @return 新的字符串
	 */
	public static String tdShow(String str, int length) {
		String tmp = str;
		if (!CheckNull.isNull(str)) {
			if (tmp.length() >= length) {
				tmp = tmp.substring(0, length - 1) + "...";
			}
		}
		return tmp;
	}

	/**
	 * 除掉中文字符
	 * 
	 * @param content
	 *            字符串
	 * @param maxSize
	 *            最大的size
	 * @return 字符
	 */
	public static String substrChinese(String content, Integer maxSize) {
		if (CheckNull.isNull(content)) {
			return "";
		}
		// 替换字符串中的全角空格为两个半角空格
		String replContent = content.replace("　", "  ");
		// 去除前后空格
		String tempContent = replContent.trim();

		StringBuffer retStr = new StringBuffer();
		if (!CheckNull.isNull(tempContent)) {
			int count = tempContent.length();
			/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
			for (int i = 0; i < count; i++) {
				if (tempContent.length() - 1 >= i) {
					/* 获取一个字符 */
					String temp = tempContent.substring(i, i + 1);
					retStr.append(temp);
					/* 匹配双字节字符(包括汉字在内) */
					if (temp.matches("[^\\x00-\\xff]")) {
						/* 双字节字符长度为2 */
						maxSize--;
					}
					if (i >= maxSize - 1) {
						break;
					}
				}
			}
			if (tempContent.length() > maxSize) {
				retStr.append("...");
			}
		}
		return retStr.toString();
	}

	/**
	 * @Title: getSumPage
	 * @Description: 获得总页数
	 * @param total
	 * @param onePageSum
	 * @return
	 * 
	 */

	/**
	 * 获得总页数
	 * 
	 * @param total
	 *            总页面
	 * @param onePageSum
	 *            一页的页数
	 * @return 页数
	 */
	public static int getSumPage(Integer total, Integer onePageSum) {
		if (CheckNull.isNull(total) || CheckNull.isNull(onePageSum)) {
			return 0;
		}

		if (onePageSum <= 0) {
			return total;
		}

		int temp = total / onePageSum;

		if (total % onePageSum != 0) {
			temp += 1;
		}
		return temp;
	}

	/**
	 * 数字转换成大写
	 * 
	 * @param digital
	 *            数字
	 * @return 大写字符串
	 */
	public static String tranStringByDigital(int digital) {
		String[] units = new String[] { "十", "百", "千", "万", "十", "百", "千", "亿" };
		String[] numeric = new String[] { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };

		if (digital == 10) {
			return "十";
		} else if (digital < 20 && digital > 10) {
			return "十" + numeric[digital % 10];
		}

		String res = "";
		String temp = String.valueOf(digital);
		// 遍历一行中所有数字
		for (int k = -1; temp.length() > 0; k++) {
			// 解析最后一位
			int j = Integer.parseInt(temp.substring(temp.length() - 1, temp.length()));
			String rtemp = numeric[j];

			// 数值不是0且不是个位 或者是万位或者是亿位 则去取单位
			if (j != 0 && k != -1 || k % 8 == 3 || k % 8 == 7) {
				rtemp += units[k % 8];
			}

			// 拼在之前的前面
			res = rtemp + res;

			// 去除最后一位
			temp = temp.substring(0, temp.length() - 1);
		}

		// 去除后面连续的零零..
		while (res.endsWith(numeric[0])) {
			res = res.substring(0, res.lastIndexOf(numeric[0]));
		}

		// 将零零替换成零
		while (res.indexOf(numeric[0] + numeric[0]) != -1) {
			res = res.replaceAll(numeric[0] + numeric[0], numeric[0]);
		}

		// 将 零+某个单位 这样的窜替换成 该单位 去掉单位前面的零
		for (int m = 1; m < units.length; m++) {
			res = res.replaceAll(numeric[0] + units[m], units[m]);
		}

		return res;
	}

	/**
	 * 字符串转成Int型，如果为空，则返回为0
	 * 
	 * @param str
	 *            字符串
	 * @return Int数据
	 */
	public static int getIntByString(String str) {
		if (CheckNull.isNull(str)) {
			return 0;
		}

		try {
			return (int) Double.parseDouble(str.trim());
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 字符串转成Double型，如果为空，则返回为0
	 * 
	 * @param str
	 *            字符串
	 * @return double数据
	 */
	public static double getDoubleByString(String str) {
		if (CheckNull.isNull(str)) {
			return 0;
		}

		try {
			return Double.parseDouble(str.trim());
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 字符串转成float型，如果为空，则返回为0
	 * 
	 * @param str
	 *            字符串
	 * @return float数据
	 */
	public static float getFloatByString(String str) {
		// 字符串为空
		if (CheckNull.isNull(str)) {
			return 0f;
		}

		try {
			return Float.parseFloat(str.trim());
		} catch (Exception e) {
			return 0f;
		}
	}

	/**
	 * 分隔字符串
	 * 
	 * @param str
	 *            字符串
	 * @param splitChar
	 *            分隔符
	 * @return 字符集合
	 */
	public static List<String> split(String str, String splitChar) {
		// 
		List<String> all = new ArrayList<String>();
		if (CheckNull.isNull(str)) {
			return all;
		}
		if (CheckNull.isNull(splitChar)) {
			splitChar = ",";
		}
		String[] strings = str.split(splitChar);
		for (String string : strings) {
			all.add(string);
		}
		return all;
	}

	/**
	 * object转成float型，如果为空，则返回为0
	 * 
	 * @param object
	 *            obj数据
	 * @return float数据
	 */
	public static Float getFloatByObject(Object object) {
		// 字符串为空
		if (CheckNull.isNull(object)) {
			return 0f;
		}

		try {
			return Float.parseFloat(object.toString());
		} catch (Exception e) {
			return 0f;
		}
	}

	/**
	 * 通过Long来获得DATE
	 * 
	 * @param l
	 *            long型
	 * @return Date数据
	 */
	public static Date getDateByLong(Long l) {
		if (CheckNull.isNull(l)) {
			return null;
		}
		return new Date(l);
	}

	/**
	 * Date类型转到Long类型
	 * 
	 * @param date
	 *            date类型
	 * @return Long类型
	 */
	public static Long getLongByDate(Date date) {
		if (CheckNull.isNull(date)) {
			return null;
		}
		return date.getTime();
	}

	/**
	 * Long转成String类型
	 * 
	 * @param l
	 *            long数据
	 * @param format
	 *            格式
	 * @return String类型
	 */
	public static String getYMDByLong(Long l, String format) {
		if (CheckNull.isNull(l)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(l));
	}

	/**
	 * 判断进来字符串是否存在（"_", "%", "+", "%25"）
	 * 
	 * @param sourceStr
	 *            字符串
	 * @return 返回
	 */
	public static String filterFormat(String sourceStr) {
		for (int i = 0; i < strArr.length; i++) {
			if (strArr[i].equals(sourceStr)) {
				sourceStr = objStr;
			}
		}
		return sourceStr;
	}

	/**
	 * 通过毫秒数来算出天
	 * 
	 * @param l
	 *            毫秒
	 * @return 天
	 */
	public static int getDayByMillisecond(long l) {
		int day = (int) (l / 1000 / 60 / 60 / 24);
		return day;
	}

	/**
	 * 两个日期相关天数
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 天数
	 */
	public static int getDifferenceByLong(long startDate, long endDate) {
		if (endDate < startDate) {
			return 0;
		}
		Calendar today = Calendar.getInstance();
		today.setTimeInMillis(endDate);
		today.set(Calendar.HOUR_OF_DAY, 23);
		today.set(Calendar.MINUTE, 59);
		today.set(Calendar.SECOND, 59);
		return (int) ((today.getTimeInMillis() - startDate) / 86400000);
	}

	/**
	 * 获得下一天的毫秒数
	 * 
	 * @return 毫秒数
	 */
	public static long getNextDayMillisecond() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTimeInMillis();
	}

	/**
	 * BigInteger转成Integer
	 * 
	 * @param o
	 *            Object数据
	 * @return Integer数据
	 */
	public static Integer getIntegerByBigIntegerObject(Object o) {
		if (CheckNull.isNull(o)) {
			return null;
		}
		Integer i = 0;
		BigInteger bi = (BigInteger) o;
		i = bi.intValue();
		return i;
	}

	/**
	 * Long转成Int
	 * 
	 * @param a
	 *            Long数据
	 * @return Int
	 */
	public static int getIntByLong(Long a) {
		int ret = 0;
		if (!CheckNull.isNull(a)) {
			ret = Integer.parseInt(a.toString());
		}
		return ret;
	}

	/**
	 * 格式化数据的小数点。默认为2位。
	 * 
	 * @param d
	 *            数据
	 * @return 字符串
	 */
	public static String tranDoubleToStr(Double d) {
		return tranDoubleToStr(d, "#.##");
	}

	/**
	 * 格式化数据的小数点。默认为1位。
	 * 
	 * @param d
	 *            数据
	 * @param format
	 *            传入格式化字符格式
	 * @return 字符串
	 * 
	 */
	public static String tranDoubleToStr(Double d, String format) {
		if (CheckNull.isNull(d)) {
			return "";
		}
		DecimalFormat df = new DecimalFormat(format);
		return df.format(d);
	}

	/**
	 * 格式化数据的小数点。默认为2位。
	 * 
	 * @param d
	 *            float数据
	 * @return 字符
	 */
	public static String tranFloatToStr(Float d) {
		return tranFloatToStr(d, "#.##");
	}

	/**
	 * 格式化数据的小数点。默认为1位。
	 * 
	 * @param d
	 *            float数据
	 * @param format
	 *            传入格式化字符格式
	 * @return 字符串
	 * 
	 */
	public static String tranFloatToStr(Float d, String format) {
		if (CheckNull.isNull(d)) {
			return "";
		}
		DecimalFormat df = new DecimalFormat(format);
		return df.format(d);
	}

	/**
	 * 创建验证码
	 * 
	 * @return 验证码
	 */
	public static String createVerifiCationCode() {
		Random r = new Random();
		int rInt = r.nextInt(900000) + 100000;
		return rInt + "";
	}

	/**
	 * 比较第三个时间是否在第一个参数和第二个参数中间，如果是为true,如果为不是false
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param time
	 *            时间
	 * @return 如果是为true,如果为不是false
	 */
	public static boolean compareTime(long startTime, long endTime, long time) {
		if (startTime <= time && endTime >= time) {
			return true;
		}
		return false;
	}

	/**
	 * 通过Map的KEy获得List序列
	 * 
	 * @param map
	 *            Map
	 * @return list
	 */
	public static List getListByMapKey(Map map) {
		if (CheckNull.isNull(map)) {
			return null;
		}

		Set keySet = map.keySet();
		List list = new ArrayList();
		for (Object object : keySet) {
			list.add(object);
		}
		return list;
	}

	/**
	 * <p>
	 * 四舍五入
	 * </p>
	 * 
	 * @author Lennon.Wang
	 * @date 2015-9-18 下午01:48:22
	 * @param d
	 * @return
	 */
	public static int getIntBydouble(double d) {
		return (int) Math.rint(d);
	}

}
