package com.meridian.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meridian.common.SimpleConfigurationParser;


public class MysqlConnect {
    private static Logger LOG = LoggerFactory.getLogger(MysqlConnect.class);

    private static String className = SimpleConfigurationParser.getInstance().getProperties().getString("Mysql.classname");
    private static String host = SimpleConfigurationParser.getInstance().getProperties().getString("Mysql.host");
    private static String port = SimpleConfigurationParser.getInstance().getProperties().getString("Mysql.port");
    private static String username = SimpleConfigurationParser.getInstance().getProperties().getString("Mysql.username");
    private static String password = SimpleConfigurationParser.getInstance().getProperties().getString("Mysql.password");
    private static String database = SimpleConfigurationParser.getInstance().getProperties().getString("Mysql.database");
    
    private static String url = null;
    
    static {
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (StringUtils.isNotBlank(host) && StringUtils.isNotBlank(port)) {
            StringBuffer urlBuffer = new StringBuffer();
            urlBuffer.append("jdbc:mysql://" + host + ":" + port);
            if (StringUtils.isNotBlank(database)) {
                urlBuffer.append("/" + database);
            }
            urlBuffer.append("?useUnicode=true&characterEncoding=UTF-8");
            url = urlBuffer.toString();
        }
    }
    
    public static Connection getConnection() {
        return getConnection(url, username, password);
    }

    public static Connection getConnection(String host, String port, String username, String password) {
        String url = "jdbc:mysql://" + host + ":" + port + "?useUnicode=true&characterEncoding=UTF-8";
        return getConnection(url, username, password);
    }

    public static Connection getConnection(String host, String port, String username, String password, String database) {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useUnicode=true&characterEncoding=UTF-8";
        return getConnection(url, username, password);
    }
    
    public static Connection getConnection(String url, String username, String password) {
        Connection conn = null;
        LOG.info(url);
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            LOG.error("MySQL error(connect)");
        }
        return conn;
    }

    public static void closeConnection(Connection conn) {
        try {
            if (null == conn) {
                return;
            } else {
                conn.close();
            }
        } catch (SQLException e) {
            LOG.error("Database Connection closed error.");
        }
    }

    public static void closeStatement(Statement stmt) {
        try {
            if (null == stmt) {
                return;
            } else {
                stmt.close();
            }
        } catch (SQLException e) {
            LOG.error("Database Statement closed error.");
        }
    }

    public static boolean isValid(Connection conn) {
        try {
            if (null == conn) {
                return false;
            } else {
                return conn.isValid(1);
            }
        } catch (SQLException e) {
            return false;
        }
    }

}
