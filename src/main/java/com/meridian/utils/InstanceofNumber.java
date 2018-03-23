package com.meridian.utils;

/**
 * @Description 是否是数字类型
 * @version 1.0
 * @date 2016年9月26日 下午4:57:29
 * @since JDK 1.7
 * @author Kevin.Sun
 * @copyright Copyright 2015 BeiJing MMEDNET. All rights reserved.
 */
public class InstanceofNumber {

	/**
	 * @Description 判断是否为整数
	 * @author Kevin.Sun
	 * @date 2016年9月26日 下午4:59:11
	 * @param value
	 * @return
	 */
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}catch (NullPointerException e) {
			return false;
		}
	}
    
    /**
     * @Description 判断是否为长整数
     * @author 刘洋
     * @param value
     * @return
     */
    public static boolean isLong(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }catch (NullPointerException e) {
            return false;
        }
    }

	/**
	 * @Description 判断是否为浮点数
	 * @author Kevin.Sun
	 * @date 2016年9月26日 下午4:58:59
	 * @param value
	 * @return
	 */
	public static boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
			if (value.contains("."))
				return true;
			return false;
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	 /**
	  * 判断字符串是否是数字
	  */
	 public static boolean isNumber(String value) {
	  return isInteger(value) || isLong(value) || isDouble(value);
	 }

}
