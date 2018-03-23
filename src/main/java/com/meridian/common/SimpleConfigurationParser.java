package com.meridian.common;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * <p><description>简单的配置解析器</description></p>
 * @author Bruce.Zhao
 * @site http://www.biubiu.net  
 * @version 1.0
 * @date 2015-7-3 
 * @since JDK 1.7
 * @copyright Copyright 2015 BeiJing MMEDNET. All rights reserved.
 */
public class SimpleConfigurationParser {
	
	/** 私有的静态实例对象  **/
	private static SimpleConfigurationParser instance = null;
	
	/** 属性文件 **/
	private Configuration properties;

	/**
	 * <p>Title: </p>
	 * <p>Description: 私有的构造器 </p>
	 */
	private SimpleConfigurationParser() {
		try {
			properties = new PropertiesConfiguration(this.getClass().getResource("/resources.properties"));
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <p>Title: getInstance</p>
	 * <p>Description: 获取简单的配置解析器对象</p>
	 * @return
	 */
	public static synchronized SimpleConfigurationParser getInstance() {
		if (instance == null) {
			instance = new SimpleConfigurationParser();
		}
		return instance;
	}

	public Configuration getProperties() {
		return properties;
	}
	
}
