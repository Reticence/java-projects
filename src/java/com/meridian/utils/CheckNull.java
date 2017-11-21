package com.meridian.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 空判断
 * </p>
 * 
 * @author Lennon.Wang
 * @site http://www.biubiu.net
 * @version 1.0
 * @date 2015-7-22
 * @since JDK 1.7
 * @copyright Copyright 2015 BeiJing MMEDNET. All rights reserved.
 */
public abstract class CheckNull {
	/**
	 * 
	 * 检查字符串是否为空 包括空字符串,去空格
	 * 
	 * @param str
	 * @return true:空 false：非空
	 */
	public static boolean isNullTrim(String str) {
		return (null == str || "".equals(str.trim()));
	}

	/**
	 * 
	 * 检查字符串是否为空 包括空字符串
	 * 
	 * @param str
	 * @return true:空 false：非空
	 */
	public static boolean isNull(String str) {
		return (null == str || "".equals(str));
	}

	/**
	 * 
	 * 检查list是否为空 包括list的size为0
	 * 
	 * @param List
	 * @return true:空 false：非空
	 */
	public static boolean isNull(List lst) {
		return null == lst || lst.isEmpty();
	}

	/**
	 * 
	 * 检查map是否为空 包括map的size为0
	 * 
	 * @param map
	 * @return
	 */
	public static boolean isNull(Map map) {
		return null == map || 0 == map.size();
	}

    /**
     * 
     * 检查set是否为空 包括set的size为0
     * 
     * @param map
     * @return
     */
    public static boolean isNull(Set set) {
        return null == set || set.isEmpty();
    }

	/**
	 * 
	 * 检查object是否为空
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isNull(Object obj) {
		return null == obj;
	}

	/**
	 * 
	 * @Title: isNullOrZero
	 * @Description: 判断Integer为空或者0
	 * @param @param integer
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isNullOrZero(Integer integer) {
		return null == integer || integer.intValue() == 0;
	}
}
